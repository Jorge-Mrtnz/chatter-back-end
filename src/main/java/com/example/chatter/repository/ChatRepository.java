package com.example.chatter.repository;


import com.example.chatter.model.Chat;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long>{
}
