package com.humanresources.hr.service;

import com.humanresources.hr.model.dto.LoginRequestDto;
import com.humanresources.hr.model.dto.LoginResponseDto;

public interface AuthService {

    LoginResponseDto login(LoginRequestDto request);
}