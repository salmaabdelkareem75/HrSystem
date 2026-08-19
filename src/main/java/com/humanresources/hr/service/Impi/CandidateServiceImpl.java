package com.humanresources.hr.service.Impi;

import com.humanresources.hr.exception.DuplicateResourceException;
import com.humanresources.hr.exception.ResourceNotFoundException;
import com.humanresources.hr.model.dto.CandidateRequestDto;
import com.humanresources.hr.model.dto.CandidateResponseDto;
import com.humanresources.hr.model.entity.CandidateEntity;
import com.humanresources.hr.repository.CandidateRepository;
import com.humanresources.hr.service.CandidateService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;

    public CandidateServiceImpl(
            CandidateRepository candidateRepository) {

        this.candidateRepository = candidateRepository;
    }

    @Override
    public List<CandidateResponseDto> getAllCandidates() {

        return candidateRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public CandidateResponseDto getCandidateById(Long id) {

        CandidateEntity candidate =
                candidateRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Candidate not found with id: " + id
                                )
                        );

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String role = authentication.getAuthorities()
                .stream()
                .findFirst()
                .get()
                .getAuthority();

        if (role.equals("ROLE_CANDIDATE")
                && !isCurrentUser(candidate)) {

            throw new AccessDeniedException(
                    "You can only access your own data"
            );
        }

        return mapToResponse(candidate);
    }

    @Override
    public CandidateResponseDto saveCandidate(
            CandidateRequestDto request) {

        CandidateEntity candidate =
                candidateRepository.findByEmail(request.getEmail())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Candidate not found with email: "
                                                + request.getEmail()
                                )
                        );

        mapToEntity(request, candidate);

        CandidateEntity savedCandidate =
                candidateRepository.save(candidate);

        return mapToResponse(savedCandidate);
    }

    @Override
    public CandidateResponseDto updateCandidate(
            Long id,
            CandidateRequestDto request) {

        CandidateEntity existingCandidate =
                candidateRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Candidate not found with id: " + id
                                )
                        );

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String role = authentication.getAuthorities()
                .stream()
                .findFirst()
                .get()
                .getAuthority();

        if (role.equals("ROLE_CANDIDATE")
                && !isCurrentUser(existingCandidate)) {

            throw new AccessDeniedException(
                    "You can only update your own data"
            );
        }

        if (!existingCandidate.getEmail().equals(request.getEmail())
                && candidateRepository.existsByEmail(request.getEmail())) {

            throw new DuplicateResourceException(
                    "Candidate with email "
                            + request.getEmail()
                            + " already exists"
            );
        }

        mapToEntity(request, existingCandidate);

        CandidateEntity updatedCandidate =
                candidateRepository.save(existingCandidate);

        return mapToResponse(updatedCandidate);
    }

    @Override
    public void deleteCandidate(Long id) {

        CandidateEntity candidate =
                candidateRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Candidate not found with id: " + id
                                )
                        );

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String role = authentication.getAuthorities()
                .stream()
                .findFirst()
                .get()
                .getAuthority();

        if (role.equals("ROLE_CANDIDATE")
                && !isCurrentUser(candidate)) {

            throw new AccessDeniedException(
                    "You can only delete your own data"
            );
        }

        candidateRepository.delete(candidate);
    }

    private boolean isCurrentUser(
            CandidateEntity candidate) {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String currentEmail =
                authentication.getName();

        return candidate.getEmail().equals(currentEmail);
    }

    private void mapToEntity(
            CandidateRequestDto request,
            CandidateEntity candidate) {

        candidate.setFirstName(request.getFirstName());
        candidate.setLastName(request.getLastName());
        candidate.setEmail(request.getEmail());
        candidate.setPhone(request.getPhone());
        candidate.setLinkedinUrl(request.getLinkedinUrl());
        candidate.setAddress(request.getAddress());
        candidate.setExperiences(request.getExperience());
        candidate.setSoftSkills(request.getSoftSkills());
        candidate.setTechnicalSkills(request.getTechnicalSkills());
        candidate.setSummary(request.getSummary());
        candidate.setEducation(request.getEducation());
    }

    private CandidateResponseDto mapToResponse(
            CandidateEntity candidate) {

        CandidateResponseDto response =
                new CandidateResponseDto();

        response.setId(candidate.getId());
        response.setFirstName(candidate.getFirstName());
        response.setLastName(candidate.getLastName());
        response.setEmail(candidate.getEmail());
        response.setPhone(candidate.getPhone());
        response.setLinkedinUrl(candidate.getLinkedinUrl());
        response.setAddress(candidate.getAddress());
        response.setExperience(candidate.getExperiences());
        response.setSoftSkills(candidate.getSoftSkills());
        response.setTechnicalSkills(candidate.getTechnicalSkills());
        response.setSummary(candidate.getSummary());
        response.setEducation(candidate.getEducation());
        response.setCreatedAt(candidate.getCreatedAt());
        response.setCreatedBy(candidate.getCreatedBy());
        response.setModifiedAt(candidate.getModifiedAt());
        response.setModifiedBy(candidate.getModifiedBy());

        return response;
    }
}