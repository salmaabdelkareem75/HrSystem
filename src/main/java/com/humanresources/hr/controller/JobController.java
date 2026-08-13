package com.humanresources.hr.controller;
import com.humanresources.hr.model.dto.JobRequestDto;
import com.humanresources.hr.model.dto.JobResponseDto;
import com.humanresources.hr.service.JobService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<JobResponseDto>> getAllJobs() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobService.getAllJobs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponseDto> getJobById(
            @PathVariable Long id) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobService.getJobById(id));
    }

    @PostMapping
    public ResponseEntity<JobResponseDto> createJob(
          @Valid @RequestBody JobRequestDto request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(jobService.createJob(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobResponseDto> updateJob(
            @PathVariable Long id,
           @Valid @RequestBody JobRequestDto request) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobService.updateJob(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(
            @PathVariable Long id) {

        jobService.deleteJob(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}

