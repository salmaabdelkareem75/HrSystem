package com.humanresources.hr.repository;

import com.humanresources.hr.model.entity.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository
        extends JpaRepository<ApplicationEntity, Long> {

    Optional<ApplicationEntity> findByIdAndCandidateEmail(
            Long id,
            String email
    );
}