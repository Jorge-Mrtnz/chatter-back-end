package com.example.chatter.controller;

import javax.websocket.server.PathParam;

import com.example.chatter.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    UserRepository userRepository;
    
    @GetMapping("findUserById")
    public ResponseEntity<?> findUserById(@PathParam("id") long id){
        return ResponseEntity.ok(userRepository.findById(id));
    }
}
