package com.myweapon.hourglass.security.service;

import com.myweapon.hourglass.repository.UserRepository;
import com.myweapon.hourglass.security.dao.JwtAuthenticationResponse;
import com.myweapon.hourglass.security.dao.SignInRequest;
import com.myweapon.hourglass.security.dao.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    JwtAuthenticationResponse signUp(SignUpRequest request);
    JwtAuthenticationResponse signIn(SignInRequest request);
}
