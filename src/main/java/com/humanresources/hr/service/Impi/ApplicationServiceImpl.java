package com.humanresources.hr.service.Impi;

import com.humanresources.hr.exception.ResourceNotFoundException;
import com.humanresources.hr.model.dto.ApplicationRequestDto;
import com.humanresources.hr.model.dto.ApplicationResponseDto;
import com.humanresources.hr.model.entity.ApplicationEntity;
import com.humanresources.hr.model.entity.CandidateEntity;
import com.humanresources.hr.model.entity.JobEntity;
import com.humanresources.hr.repository.ApplicationRepository;
import com.humanresources.hr.repository.CandidateRepository;
import com.humanresources.hr.repository.JobRepository;
import com.humanresources.hr.service.ApplicationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final CandidateRepository candidateRepository;

    public ApplicationServiceImpl(
            ApplicationRepository applicationRepository,
            JobRepository jobRepository,
            CandidateRepository candidateRepository) {

        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.candidateRepository = candidateRepository;
    }

    @Override
    public List<ApplicationResponseDto> getAllApplications() {

        return applicationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ApplicationResponseDto getApplicationById(Long id) {

        ApplicationEntity application;

        if (isCandidate()) {

            String email = getCurrentUserEmail();

            application = applicationRepository
                    .findByIdAndCandidateEmail(id, email)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Application not found or you do not have access to it"
                            )
                    );

        } else {

            application = applicationRepository.findById(id)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Application not found with id: " + id
                            )
                    );
        }

        return mapToResponse(application);
    }

    @Override
    public ApplicationResponseDto saveApplication(
            ApplicationRequestDto request) {

        JobEntity job = jobRepository.findById(request.getJobId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Job not found with id: "
                                        + request.getJobId()
                        )
                );

        String email = getCurrentUserEmail();

        CandidateEntity candidate = candidateRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Candidate not found with email: " + email
                        )
                );

        ApplicationEntity application =
                new ApplicationEntity();

        application.setJob(job);
        application.setCandidate(candidate);
        application.setStatus("PENDING");

        ApplicationEntity savedApplication =
                applicationRepository.save(application);

        return mapToResponse(savedApplication);
    }

    @Override
    public ApplicationResponseDto updateApplication(
            Long id,
            ApplicationRequestDto request) {

        String email = getCurrentUserEmail();

        ApplicationEntity application =
                applicationRepository
                        .findByIdAndCandidateEmail(id, email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Application not found or you do not have access to it"
                                )
                        );

        JobEntity job = jobRepository.findById(request.getJobId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Job not found with id: "
                                        + request.getJobId()
                        )
                );

        application.setJob(job);

        ApplicationEntity updatedApplication =
                applicationRepository.save(application);

        return mapToResponse(updatedApplication);
    }

    @Override
    public void withdrawApplication(Long id) {

        String email = getCurrentUserEmail();

        ApplicationEntity application =
                applicationRepository
                        .findByIdAndCandidateEmail(id, email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Application not found or you do not have access to it"
                                )
                        );

        application.setStatus("WITHDRAWN");

        applicationRepository.save(application);
    }

    private String getCurrentUserEmail() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        return authentication.getName();
    }

    private boolean isCandidate() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        return authentication.getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority.getAuthority()
                                .equals("ROLE_CANDIDATE"));
    }

    private ApplicationResponseDto mapToResponse(
            ApplicationEntity application) {

        ApplicationResponseDto response =
                new ApplicationResponseDto();

        response.setId(application.getId());
        response.setJobId(application.getJob().getId());
        response.setCandidateId(
                application.getCandidate().getId()
        );
        response.setStatus(application.getStatus());

        return response;
    }
}