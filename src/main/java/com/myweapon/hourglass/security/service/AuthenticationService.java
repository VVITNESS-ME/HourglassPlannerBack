package com.myweapon.hourglass.security.service;

import com.myweapon.hourglass.security.dto.JwtAuthenticationResponse;
import com.myweapon.hourglass.security.dto.SignInRequest;
import com.myweapon.hourglass.security.dto.SignUpRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);
    JwtAuthenticationResponse signin(SignInRequest request);
}
