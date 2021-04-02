package com.example.chatter.repository;

import java.util.List;

import com.example.chatter.model.Conversation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Long>{
    List<Conversation> findByName(String name);
}
