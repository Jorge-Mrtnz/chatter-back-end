package com.example.chatter.repository;

import java.util.List;

import com.example.chatter.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    
    public List<User> findByUsername(String username);
}
