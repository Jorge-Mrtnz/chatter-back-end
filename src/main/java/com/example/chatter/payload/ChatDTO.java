package com.example.chatter.payload;

import java.util.Date;

import lombok.Data;

@Data
public class ChatDTO {
    private Long id;
    private String file;
    private Long conversationId;
    private Date dateCreated;
    private Long creatorId;
}
