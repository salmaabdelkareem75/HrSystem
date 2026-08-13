package com.humanresources.hr.model.dto;

import lombok.Data;

@Data
public class JobResponseDto {

    private Long id;

    private String title;

    private String description;

    private String department;

    private String status;

    private String assignedHr;
}