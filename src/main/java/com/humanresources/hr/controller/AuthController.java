package com.humanresources.hr.controller;


import com.humanresources.hr.model.dto.JobRequestDto;
import com.humanresources.hr.model.dto.LoginRequestDto;
import com.humanresources.hr.model.dto.LoginResponseDto;
import com.humanresources.hr.service.AuthService;
import com.humanresources.hr.service.CandidateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> createJob(
            @Valid @RequestBody LoginRequestDto request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.login(request));
    }

}
