package com.humanresources.hr.controller;
import com.humanresources.hr.model.dto.CandidateRequestDto;
import com.humanresources.hr.model.dto.CandidateResponseDto;
import com.humanresources.hr.service.CandidateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidates")
class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping
    ResponseEntity<List<CandidateResponseDto>> getAllCandidates() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(candidateService.getAllCandidates());
    }

    @GetMapping("/{id}")
    ResponseEntity<CandidateResponseDto> getCandidateById(
            @PathVariable Long id) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(candidateService.getCandidateById(id));
    }

    @PostMapping
    ResponseEntity<CandidateResponseDto> createCandidate(
           @Valid @RequestBody CandidateRequestDto request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(candidateService.saveCandidate(request));
    }

    @PutMapping("/{id}")
    ResponseEntity<CandidateResponseDto> updateCandidate(
            @PathVariable Long id,
            @Valid  @RequestBody CandidateRequestDto request) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(candidateService.updateCandidate(id, request));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCandidate(
            @PathVariable Long id) {

        candidateService.deleteCandidate(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
