package com.example.chatter.repository;

import com.example.chatter.model.Conversation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Conversation, Long>{
    
}
