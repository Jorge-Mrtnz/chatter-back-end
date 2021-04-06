package com.example.chatter.controller;

import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import com.example.chatter.model.User;
import com.example.chatter.payload.Message;
import com.example.chatter.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@CrossOrigin
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;
    
    @PostMapping("follow")
    public ResponseEntity<?> follow(@RequestBody User target, Principal principal) {
        userService.followUser(target, principal);
        return ResponseEntity.ok(new Message("Follow succesfull"));
    }

    @GetMapping("getFollowing")
    public ResponseEntity<?> getFollowing(Principal principal){
        return ResponseEntity.ok(userService.getFollowers(principal));
    }
}
