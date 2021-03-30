package com.example.chatter.repository;

import com.example.chatter.model.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection, Long>{
    
}
