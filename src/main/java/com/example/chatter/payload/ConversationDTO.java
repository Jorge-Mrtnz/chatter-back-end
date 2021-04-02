package com.example.chatter.payload;

import java.util.Date;

import lombok.Data;

@Data
public class ConversationDTO {
    private Long id;
    private String name;
    private String image;
    private Date dateCreated;
    private String owner;
}
