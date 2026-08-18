package com.humanresources.hr.model.dto;
import com.humanresources.hr.model.entity.ExperienceEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import tools.jackson.databind.JsonNode;

import java.util.List;

@Data
public class CandidateRequestDto {

    @NotBlank(message = "First-Name cannot be empty")
    private String firstName;

    @NotBlank(message = "last-Name cannot be empty")
    private String lastName;

    @Email(message = "Invalid Email,Email cannot be empty")
    private String email;

    @Pattern(
            regexp = "^(01[0125][0-9]{8}|\\+201[0125][0-9]{8})$",
            message = "Phone number must be a valid Egyptian mobile number"
    )
    private String phone;

    @Pattern(
            regexp = "^(https?://)?(www\\.)?linkedin\\.com/.*$",
            message = "LinkedIn URL must be a valid LinkedIn link"
    )
    private String linkedinUrl;

    @NotBlank
    private String address;

    private List<ExperienceEntity> experience;

    @NotBlank
    private String softSkills;

    @NotBlank
    private String technicalSkills;

    @NotBlank
    private String summary;

    @NotBlank
    private String education;

    @NotBlank
    @Size(min = 8, max = 25, message = "Password must be at least 8 characters")
    private String password;
}