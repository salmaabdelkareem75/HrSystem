package com.humanresources.hr.controller;

import com.humanresources.hr.model.dto.ApplicationRequestDto;
import com.humanresources.hr.model.dto.ApplicationResponseDto;
import com.humanresources.hr.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(
            ApplicationService applicationService) {

        this.applicationService = applicationService;
    }

    @GetMapping
    ResponseEntity<List<ApplicationResponseDto>> getAllApplications() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(applicationService.getAllApplications());
    }

    @GetMapping("/{id}")
    ResponseEntity<ApplicationResponseDto> getApplicationById(
            @PathVariable Long id) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(applicationService.getApplicationById(id));
    }

    @PostMapping
    ResponseEntity<ApplicationResponseDto> createApplication(
            @Valid @RequestBody ApplicationRequestDto request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(applicationService.saveApplication(request));
    }

    @PutMapping("/{id}")
    ResponseEntity<ApplicationResponseDto> updateApplication(
            @PathVariable Long id,
            @Valid @RequestBody ApplicationRequestDto request) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(applicationService.updateApplication(
                        id,
                        request
                ));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteApplication(
            @PathVariable Long id) {

        applicationService.withdrawApplication(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}