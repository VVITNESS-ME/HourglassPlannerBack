package com.myweapon.hourglass.security.controller;

import com.myweapon.hourglass.common.dto.ApiResponse;
import com.myweapon.hourglass.security.dto.JwtAuthenticationResponse;
import com.myweapon.hourglass.security.dto.SignInRequest;
import com.myweapon.hourglass.security.dto.SignUpRequest;
import com.myweapon.hourglass.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/signup")

    public ResponseEntity<ApiResponse<JwtAuthenticationResponse>>  signup(@RequestBody SignUpRequest request){
        return authenticationService.signup(request);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtAuthenticationResponse>> signin(@RequestBody SignInRequest request){
        return authenticationService.signin(request);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("is valid API Test");
    }
}
