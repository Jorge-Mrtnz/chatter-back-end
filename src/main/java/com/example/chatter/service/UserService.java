package com.example.chatter.service;

import javax.transaction.Transactional;

import com.example.chatter.model.AuthRequest;
import com.example.chatter.model.User;
import com.example.chatter.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
 
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void createUser(AuthRequest req){
        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        userRepository.save(user);
    }
}
