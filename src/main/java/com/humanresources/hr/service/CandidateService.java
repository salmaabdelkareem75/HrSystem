package com.humanresources.hr.service;

import com.humanresources.hr.model.dto.CandidateRequestDto;
import com.humanresources.hr.model.dto.CandidateResponseDto;

import java.util.List;

public interface CandidateService {

    List<CandidateResponseDto> getAllCandidates();

    CandidateResponseDto getCandidateById(Long id);

    CandidateResponseDto saveCandidate(CandidateRequestDto request);

    CandidateResponseDto updateCandidate(Long id, CandidateRequestDto request);

    void deleteCandidate(Long id);
}