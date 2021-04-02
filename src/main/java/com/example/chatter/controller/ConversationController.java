package com.example.chatter.controller;

import java.security.Principal;
import java.util.List;

import javax.websocket.server.PathParam;

import com.example.chatter.model.Conversation;
import com.example.chatter.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("conversation")
public class ConversationController {

    @Autowired
    private PostService postService;

    @PostMapping("create")
    public ResponseEntity<?> newCollection(@RequestBody Conversation conv, Principal principal){
        postService.createConversation(conv, principal.getName());
        return ResponseEntity.ok("Conversation Created Successfully");
    }

    @GetMapping("findByName")
    public List<Conversation> getConversation(@PathParam("name") String name) {
        return postService.getConversationByName(name);
    }
    
}
