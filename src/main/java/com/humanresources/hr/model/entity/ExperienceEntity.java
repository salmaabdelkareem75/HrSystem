package com.humanresources.hr.model.entity;

import com.humanresources.hr.model.entity.CandidateEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "experience")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    //start date
    //end date
    private String description;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private CandidateEntity candidate;
}