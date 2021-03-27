package com.example.auth.model;

import lombok.Data;

@Data
public class AuthResponse {
    private final String jwtToken;
}
