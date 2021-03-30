package com.example.chatter.repository;

import com.example.chatter.model.Post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>{
    
}
