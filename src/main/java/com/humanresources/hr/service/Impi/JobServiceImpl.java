package com.humanresources.hr.service.Impi;

import com.humanresources.hr.exception.ResourceNotFoundException;
import com.humanresources.hr.model.dto.JobRequestDto;
import com.humanresources.hr.model.dto.JobResponseDto;
import com.humanresources.hr.model.entity.JobEntity;
import com.humanresources.hr.repository.JobRepository;
import com.humanresources.hr.service.JobService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<JobResponseDto> getAllJobs() {

        return jobRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public JobResponseDto getJobById(Long id) {

        JobEntity job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Job not found with id: " + id
                        )
                );

        return mapToResponse(job);
    }

    @Override
    public JobResponseDto createJob(JobRequestDto request) {

        JobEntity job = new JobEntity();

        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setDepartment(request.getDepartment());

        JobEntity savedJob = jobRepository.save(job);

        return mapToResponse(savedJob);
    }

    @Override
    public JobResponseDto updateJob(
            Long id,
            JobRequestDto request) {

        JobEntity existingJob = jobRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Job not found with id: " + id
                        )
                );

        existingJob.setTitle(request.getTitle());
        existingJob.setDescription(request.getDescription());
        existingJob.setDepartment(request.getDepartment());

        JobEntity updatedJob = jobRepository.save(existingJob);

        return mapToResponse(updatedJob);
    }

    @Override
    public void deleteJob(Long id) {

        JobEntity job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Job not found with id: " + id
                        )
                );

        jobRepository.delete(job);
    }

    private JobResponseDto mapToResponse(JobEntity job) {

        JobResponseDto response = new JobResponseDto();

        response.setId(job.getId());
        response.setTitle(job.getTitle());
        response.setDescription(job.getDescription());
        response.setDepartment(job.getDepartment());
        response.setStatus(job.getStatus());
        response.setAssignedHr(job.getAssignedHr());

        return response;
    }
}