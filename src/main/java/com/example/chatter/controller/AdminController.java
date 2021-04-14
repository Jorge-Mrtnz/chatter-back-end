package com.example.chatter.controller;

import javax.websocket.server.PathParam;

import com.example.chatter.payload.Message;
import com.example.chatter.repository.UserRepository;
import com.example.chatter.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostService postService;
    
    @GetMapping("findUserById")
    public ResponseEntity<?> findUserById(@PathParam("id") long id){
        return ResponseEntity.ok(userRepository.findById(id));
    }

    @DeleteMapping("delete")
    public ResponseEntity<Message> deleteConversation(@PathParam("convId") Long convId){
        System.out.println(convId);
        postService.deleteConversation(convId);
        return ResponseEntity.ok(new Message("Conversation Created Successfully"));
    }

}
