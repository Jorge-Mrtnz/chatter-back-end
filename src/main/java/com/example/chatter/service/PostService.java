package com.example.chatter.service;

import java.util.List;

import javax.transaction.Transactional;

import com.example.chatter.model.Collection;
import com.example.chatter.model.Post;
import com.example.chatter.model.User;
import com.example.chatter.repository.CollectionRepository;
import com.example.chatter.repository.PostRepository;
import com.example.chatter.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CollectionRepository collectionRepository;

    @Autowired
    UserRepository userRepository;
    
    @Transactional
    public void createPost(Post p){
        // List<User> user = userRepository.findByUsername(username);
        postRepository.save(p);
    }

    @Transactional
    public void createCollection(String name, String username){
        List<User> user = userRepository.findByUsername(username);
        Collection c = new Collection();
        c.setName(name);
        c.setOwner(user.get(0));
        collectionRepository.save(c);
    }
}
