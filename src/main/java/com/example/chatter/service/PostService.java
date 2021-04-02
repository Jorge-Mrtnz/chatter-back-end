package com.example.chatter.service;

import java.security.Principal;
import java.util.List;

import javax.transaction.Transactional;

import com.example.chatter.model.Chat;
import com.example.chatter.model.Conversation;
import com.example.chatter.model.User;
import com.example.chatter.payload.ChatDTO;
import com.example.chatter.repository.ConversationRepository;
import com.example.chatter.repository.ChatRepository;
import com.example.chatter.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class PostService {

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    ConversationRepository convRepository;

    @Autowired
    UserRepository userRepository;
    
    @Transactional
    public void createChat(ChatDTO chatDTO, Principal principal){
        Chat chat = new Chat();
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        Conversation conv = convRepository.findById(chatDTO.getConversationId()).orElseThrow();
        chat.setCreator(user);
        chat.setConversation(conv);
        chat.setFile(chatDTO.getFile());
        chatRepository.save(chat);
    }

    @Transactional
    public void createConversation(Conversation conv, String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        conv.setOwner(user);
        convRepository.save(conv);
    }

    @Transactional
    public List<Conversation> getConversationByName(String name){
        return convRepository.findByName(name);
    }

    @Transactional
    public Conversation findConversationById(long id){
        return convRepository.findById(id).orElseThrow(() -> new RuntimeException("conversation not found"));
    }

    @Transactional
    public Chat findChatById(long id){
        return chatRepository.findById(id).orElseThrow(() -> new RuntimeException("chat not found"));
    }
}
