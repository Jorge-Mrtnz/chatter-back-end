package com.example.chatter.payload;

import lombok.Data;

@Data
public class AuthResponse {
    private final String jwtToken;
}
