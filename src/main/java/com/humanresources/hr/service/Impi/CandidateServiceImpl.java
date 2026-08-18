package com.humanresources.hr.service.Impi;

import com.humanresources.hr.exception.DuplicateResourceException;
import com.humanresources.hr.exception.ResourceNotFoundException;
import com.humanresources.hr.model.dto.CandidateRequestDto;
import com.humanresources.hr.model.dto.CandidateResponseDto;
import com.humanresources.hr.model.entity.CandidateEntity;
import com.humanresources.hr.model.entity.Role;
import com.humanresources.hr.repository.CandidateRepository;
import com.humanresources.hr.service.CandidateService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final PasswordEncoder passwordEncoder;

    public CandidateServiceImpl(
            CandidateRepository candidateRepository,
            PasswordEncoder passwordEncoder) {

        this.candidateRepository = candidateRepository;
        this.passwordEncoder = passwordEncoder;
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

        CandidateEntity candidate = candidateRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Candidate not found with id: " + id
                        )
                );

        return mapToResponse(candidate);
    }

    @Override
    public CandidateResponseDto saveCandidate(CandidateRequestDto request) {

        if (candidateRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "Candidate with email " + request.getEmail() + " already exists"
            );
        }

        CandidateEntity candidate = new CandidateEntity();

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

        if (!existingCandidate.getEmail().equals(request.getEmail())
                && candidateRepository.existsByEmail(request.getEmail())) {

            throw new DuplicateResourceException(
                    "Candidate with email " + request.getEmail() + " already exists"
            );
        }

        mapToEntity(request, existingCandidate);

        CandidateEntity updatedCandidate =
                candidateRepository.save(existingCandidate);

        return mapToResponse(updatedCandidate);
    }

    @Override
    public void deleteCandidate(Long id) {

        CandidateEntity candidate = candidateRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Candidate not found with id: " + id
                        )
                );

        candidateRepository.delete(candidate);
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

        candidate.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        candidate.setRole(Role.CANDIDATE);
    }

    private CandidateResponseDto mapToResponse(
            CandidateEntity candidate) {

        CandidateResponseDto response = new CandidateResponseDto();

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