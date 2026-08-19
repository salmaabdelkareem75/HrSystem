package com.humanresources.hr.service;

import com.humanresources.hr.model.dto.ApplicationRequestDto;
import com.humanresources.hr.model.dto.ApplicationResponseDto;

import java.util.List;

public interface ApplicationService {

    List<ApplicationResponseDto> getAllApplications();

    ApplicationResponseDto getApplicationById(Long id);

    ApplicationResponseDto saveApplication(
            ApplicationRequestDto request);

    ApplicationResponseDto updateApplication(
            Long id,
            ApplicationRequestDto request);

    void withdrawApplication(Long id);
}