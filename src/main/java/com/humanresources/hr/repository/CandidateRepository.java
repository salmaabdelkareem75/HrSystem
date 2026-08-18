package com.humanresources.hr.repository;

import com.humanresources.hr.model.entity.CandidateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateRepository extends JpaRepository<CandidateEntity, Long> {

    boolean existsByEmail(String email);

    Optional<CandidateEntity> findByEmail(String email);
}