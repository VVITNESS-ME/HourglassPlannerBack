package com.myweapon.hourglass.webchat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @GetMapping("/api/hello")
    public ResponseEntity<String> hello() {
        ResponseEntity<String> response = ResponseEntity.ok("Hello, World!");
        return response;
    }
}
