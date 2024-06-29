package com.myweapon.hourglass.security.service;

import com.myweapon.hourglass.entity.User;
import com.myweapon.hourglass.repository.UserRepository;
import com.myweapon.hourglass.security.dao.JwtAuthenticationResponse;
import com.myweapon.hourglass.security.dao.SignInRequest;
import com.myweapon.hourglass.security.dao.SignUpRequest;
import com.myweapon.hourglass.security.jwt.JwtService;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .name(request.getName())
                .build();
        userRepository.save(user);
        String jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().authToken(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        User user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(()->new IllegalArgumentException("Invalid email or password"));
        String jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().authToken(jwt).build();
    }
}
