package com.example.chatter.controller;

import java.util.List;

import com.example.chatter.model.Conversation;
import com.example.chatter.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("public")
public class PublicController {

    @Autowired
    PostService postService;
    
    @GetMapping("featured")
    public List<Conversation> getFeatured(){

        return postService.findAllConversations();
    }
}
