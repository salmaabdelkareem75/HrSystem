package com.humanresources.hr.model.dto;

import com.humanresources.hr.model.entity.ExperienceEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CandidateResponseDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String linkedinUrl;

    private String address;

    private List<ExperienceEntity> experience;

    private String softSkills;

    private String technicalSkills;

    private String summary;

    private String education;

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime modifiedAt;

    private String modifiedBy;

    public void setExperience(List<ExperienceEntity> experiences) {
    }
}