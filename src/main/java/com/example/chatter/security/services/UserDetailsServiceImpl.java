package com.example.chatter.security.services;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import com.example.chatter.model.ERole;
import com.example.chatter.model.Role;
import com.example.chatter.model.User;
import com.example.chatter.repository.RoleRepository;
import com.example.chatter.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with that username"));
            return new UserDetailsImpl(user);
    }

    @Transactional
    public void saveUser(User user){
        userRepository.save(user);
    }

    @Transactional
    public User getUser(String username) throws UsernameNotFoundException{
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with that username"));
    }

    @Transactional
    public User createUser(String email, String username, String password, Boolean enabled){
        Set<Role> roles = new HashSet<Role>();
        User user = new User();
        Role role = roleRepository.findByName(ERole.ROLE_USER)
            .orElseThrow(() -> new RuntimeException("Error: Role not found"));
        roles.add(role);

        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setRoles(roles);
        user.setEnabled(enabled);
        return user;
    }
    
}
