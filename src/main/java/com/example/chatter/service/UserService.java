package com.example.chatter.service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.example.chatter.model.User;
import com.example.chatter.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    UserRepository userRepository;

    @Transactional
    public void followUser(User _target, Principal principal){
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();
        User target = userRepository.findByUsername(_target.getUsername()).orElseThrow();
        user.getFollowing().add(target);
        // target.getFollowers().add(user);
        userRepository.save(user);
    }

    @Transactional
    public List<User> getFollowers(Principal principal){
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();
        return user.getFollowers().stream().collect(Collectors.toList());
    }
}
