package com.humanresources.hr.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "candidate")
public class CandidateEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true)
    private String email;

    private String phone;

    @Column(name = "linkedin_url")
    private String linkedinUrl;

    private String address;

    @OneToMany(mappedBy = "candidate")
    private List<ExperienceEntity> experiences;

    @Column(name = "soft_skills")
    private String softSkills;

    @Column(name = "technical_skills")
    private String technicalSkills;

    private String summary;

    private String education;

    private String password;
}