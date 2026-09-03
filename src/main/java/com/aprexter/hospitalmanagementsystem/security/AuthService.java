package com.aprexter.hospitalmanagementsystem.security;

import com.aprexter.hospitalmanagementsystem.dto.LoginRequestDto;
import com.aprexter.hospitalmanagementsystem.dto.LoginResponseDto;
import com.aprexter.hospitalmanagementsystem.dto.SignUpRequestDto;
import com.aprexter.hospitalmanagementsystem.dto.SignUpResponseDto;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public @Nullable LoginResponseDto login(LoginRequestDto loginRequestDto) {
        return null;
    }

    public @Nullable SignUpResponseDto signup(SignUpRequestDto signupRequestDto) {
        return null;
    }
}
