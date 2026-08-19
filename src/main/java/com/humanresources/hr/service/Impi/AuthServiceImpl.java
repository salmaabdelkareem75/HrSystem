package com.humanresources.hr.service.Impi;

import com.humanresources.hr.exception.DuplicateResourceException;
import com.humanresources.hr.model.dto.LoginRequestDto;
import com.humanresources.hr.model.dto.LoginResponseDto;
import com.humanresources.hr.model.dto.RegisterRequestDto;
import com.humanresources.hr.model.entity.CandidateEntity;
import com.humanresources.hr.model.entity.Role;
import com.humanresources.hr.repository.CandidateRepository;
import com.humanresources.hr.security.JwtService;
import com.humanresources.hr.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final CandidateRepository candidateRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UserDetailsService userDetailsService,
            CandidateRepository candidateRepository,
            PasswordEncoder passwordEncoder) {

        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.candidateRepository = candidateRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(
                        request.getEmail()
                );

        String token =
                jwtService.generateToken(userDetails);

        return new LoginResponseDto(token);
    }

    @Override
    public void register(RegisterRequestDto request) {

        if (candidateRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "Candidate with email " + request.getEmail() + " already exists"
            );
        }

        CandidateEntity candidate = new CandidateEntity();

        candidate.setEmail(request.getEmail());

        candidate.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        candidate.setRole(Role.CANDIDATE);

        candidateRepository.save(candidate);
    }
}