package com.myweapon.hourglass.security.service;

import com.myweapon.hourglass.common.dto.ApiResponse;
import com.myweapon.hourglass.security.dto.JwtAuthenticationResponse;
import com.myweapon.hourglass.security.dto.SignInRequest;
import com.myweapon.hourglass.security.dto.SignUpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    ResponseEntity<ApiResponse<JwtAuthenticationResponse>>  signup(SignUpRequest request);
    ResponseEntity<ApiResponse<JwtAuthenticationResponse>> signin(SignInRequest request);
}
