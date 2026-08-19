package com.humanresources.hr.service;
import com.humanresources.hr.model.dto.LoginRequestDto;
import com.humanresources.hr.model.dto.LoginResponseDto;
import com.humanresources.hr.model.dto.RegisterRequestDto;

public interface AuthService {

    LoginResponseDto login(LoginRequestDto request);
    void register(RegisterRequestDto request);}