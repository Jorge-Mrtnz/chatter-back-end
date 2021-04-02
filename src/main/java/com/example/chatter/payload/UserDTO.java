package com.example.chatter.payload;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
}
