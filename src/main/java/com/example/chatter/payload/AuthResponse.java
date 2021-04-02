package com.example.chatter.payload;

import java.util.List;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String email;
	private List<String> roles;
}
