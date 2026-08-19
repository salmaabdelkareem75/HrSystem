package com.humanresources.hr.service.Impi;

import com.humanresources.hr.model.entity.CandidateEntity;
import com.humanresources.hr.repository.CandidateRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.humanresources.hr.users.StaticUsers;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CandidateRepository candidateRepository;



    public CustomUserDetailsService(
            CandidateRepository candidateRepository) {

        this.candidateRepository = candidateRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        // First check HR / Admin static users
        return StaticUsers.USERS.stream()
                .filter(user ->
                        user.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .map(user -> User.builder()
                        .username(user.getEmail())
                        .password(user.getPassword())
                        .roles(user.getRole())
                        .build()
                )
                .orElseGet(() -> {

                    // If not HR or Admin, check Candidate database
                    CandidateEntity candidate = candidateRepository
                            .findByEmail(email)
                            .orElseThrow(() ->
                                    new UsernameNotFoundException(
                                            "User not found with email: " + email
                                    )
                            );

                    return User.builder()
                            .username(candidate.getEmail())
                            .password(candidate.getPassword())
                            .roles(candidate.getRole().name())
                            .build();
                });
    }
}