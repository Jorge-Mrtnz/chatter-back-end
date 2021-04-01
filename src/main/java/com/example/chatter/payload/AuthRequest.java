package com.example.chatter.payload;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AuthRequest implements Serializable{

    private static final long serialVersionUID = 1L;

    @NotBlank
    private String username;
    @Email
    private String email;
    @NotBlank
    private String password;
    
}
