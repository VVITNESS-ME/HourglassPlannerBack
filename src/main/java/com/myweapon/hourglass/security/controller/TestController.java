package com.myweapon.hourglass.security.controller;

import com.myweapon.hourglass.security.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    @GetMapping("/")
    public ResponseEntity<TestDto> signup(@RequestBody SignUpRequest request){
        return ResponseEntity.ok(TestDto.builder().result("성공").build());
    }
    @PostMapping("/")
    public ResponseEntity<TestDto> hi(@RequestBody SignUpRequest request){
        return ResponseEntity.ok(TestDto.builder().result("성공").build());
    }
}
