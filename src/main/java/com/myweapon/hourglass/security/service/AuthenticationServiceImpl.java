package com.myweapon.hourglass.security.service;

import com.myweapon.hourglass.Exception.RestApiException;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.security.enumset.ErrorType;
import com.myweapon.hourglass.security.repository.UserRepository;
import com.myweapon.hourglass.security.dto.JwtAuthenticationResponse;
import com.myweapon.hourglass.security.dto.SignInRequest;
import com.myweapon.hourglass.security.dto.SignUpRequest;
import com.myweapon.hourglass.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        Optional<User> foundByEmail = userRepository.findUserByEmail(request.getEmail());
        if(foundByEmail.isPresent()){
            throw new RestApiException(ErrorType.DUPLICATED_EMAIL);
        }

        Optional<User> foundByName = userRepository.findUserByName(request.getName());
        if(foundByName.isPresent()){
            throw new RestApiException(ErrorType.DUPLICATED_NAME);
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .build();

        userRepository.save(user);
        String jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().authToken(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        User user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(()->new IllegalArgumentException("Invalid email or password"));
        String jwt = jwtService.generateToken(user);
//        System.out.println(jwt);
        return JwtAuthenticationResponse.builder().authToken(jwt).build();
    }
}
