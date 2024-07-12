package com.myweapon.hourglass.webchat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/api/hello")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("hello world!");
    }
    @GetMapping("/api/health-check")
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.ok("health-check");
    }
}
