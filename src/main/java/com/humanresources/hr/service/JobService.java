package com.humanresources.hr.service;

import com.humanresources.hr.model.dto.JobRequestDto;
import com.humanresources.hr.model.dto.JobResponseDto;

import java.util.List;

public interface JobService {

    List<JobResponseDto> getAllJobs();

    JobResponseDto getJobById(Long id);

    JobResponseDto createJob(JobRequestDto request);

    JobResponseDto updateJob(Long id, JobRequestDto request);

    void deleteJob(Long id);
}