package com.example.chatter.repository;

import java.util.Optional;

import com.example.chatter.model.ERole;
import com.example.chatter.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    Optional<Role> findByName(ERole name);
}
