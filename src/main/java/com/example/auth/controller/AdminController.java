package com.example.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    
    @GetMapping("admin")
    public ResponseEntity<?> admin(){
        return ResponseEntity.ok("accessed admin controller");
    }
}
