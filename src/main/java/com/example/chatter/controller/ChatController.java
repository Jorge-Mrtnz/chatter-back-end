package com.example.chatter.controller;

import java.security.Principal;

import com.example.chatter.payload.ChatDTO;
import com.example.chatter.payload.Message;
import com.example.chatter.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("chat")
public class ChatController {
    
    @Autowired
    private PostService postService;

    @PostMapping("create")
    public ResponseEntity<?> createChat(@RequestBody ChatDTO chat, Principal principal){
        postService.createChat(chat, principal);
        return ResponseEntity.ok(new Message("Chat Inserted Successfully"));
    }

    @GetMapping("get")
    public ResponseEntity<?> getChat(@RequestParam("id") long id){
        return ResponseEntity.ok(postService.findChatById(id));
    }
}
