package com.example.chatter.model;

import lombok.Data;

@Data
public class AuthResponse {
    private final String jwtToken;
}
