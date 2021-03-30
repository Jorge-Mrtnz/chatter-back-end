package com.example.chatter.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class UserController {
    
    @GetMapping("user")
    public ResponseEntity<?> user() {
        return ResponseEntity.ok("user controller accessed");
    }
}
