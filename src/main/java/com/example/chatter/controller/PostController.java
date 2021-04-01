package com.example.chatter.controller;

import java.security.Principal;

import com.example.chatter.dto.CollectionDTO;
import com.example.chatter.model.Chat;
import com.example.chatter.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/post/create")
    public void post(@RequestBody Chat p, Principal principal){
        postService.createPost(p);
    }

    @PostMapping("/collection/create")
    public void newCollection(@RequestBody CollectionDTO dto, Principal principal){
        postService.createCollection(dto.getName(), principal.getName());
    }
    
}
