package com.myweapon.hourglass.webchat.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test/hello")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("hello world!");
    }
    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.ok("health-check");
    }
}
