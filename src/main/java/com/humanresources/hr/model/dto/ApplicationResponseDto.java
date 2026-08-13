package com.humanresources.hr.model.dto;

import lombok.Data;

@Data
public class ApplicationResponseDto {

    private Long id;

    private Long jobId;

    private Long candidateId;

    private String status;
}