package com.myweapon.hourglass.security.service;

import com.myweapon.hourglass.common.ApiResponse;
import com.myweapon.hourglass.RestApiException;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.security.enumset.ErrorType;
import com.myweapon.hourglass.security.repository.UserRepository;
import com.myweapon.hourglass.security.dto.JwtAuthenticationResponse;
import com.myweapon.hourglass.security.dto.SignInRequest;
import com.myweapon.hourglass.security.dto.SignUpRequest;
import com.myweapon.hourglass.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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
    public ResponseEntity<ApiResponse<JwtAuthenticationResponse>> signup(SignUpRequest request) {
        Optional<User> foundByEmail = userRepository.findUserByEmail(request.getEmail());
        if(foundByEmail.isPresent()){
            throw new RestApiException(ErrorType.DUPLICATED_EMAIL);
        }

        Optional<User> foundByName = userRepository.findUserByName(request.getName());
        if(foundByName.isPresent()){
            throw new RestApiException(ErrorType.DUPLICATED_NAME);
        }

        User user = User.of(request.getEmail(),passwordEncoder.encode(request.getPassword()), request.getName());

        userRepository.save(user);
        String jwt = jwtService.generateToken(user);
        JwtAuthenticationResponse data = JwtAuthenticationResponse.of(jwt);
        return ResponseEntity.ok(ApiResponse.<JwtAuthenticationResponse>success(data));
    }

    @Override
    public ResponseEntity<ApiResponse<JwtAuthenticationResponse>> signin(SignInRequest request) {
        User user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(()-> new RestApiException(ErrorType.NO_EMAIL_OR_PASSWORD));

        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new RestApiException(ErrorType.NO_EMAIL_OR_PASSWORD);
        }
        String jwt = jwtService.generateToken(user);
        JwtAuthenticationResponse data = JwtAuthenticationResponse.of(jwt);
        return ResponseEntity.ok(ApiResponse.<JwtAuthenticationResponse>success(data));
    }
}
