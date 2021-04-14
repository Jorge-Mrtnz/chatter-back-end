package com.example.chatter.service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.example.chatter.model.Chat;
import com.example.chatter.model.Conversation;
import com.example.chatter.model.User;
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
    public long createChat(long id, Principal principal){
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Conversation conv = convRepository.findById(id).orElseThrow(() -> new RuntimeException("Conversation not found"));
        Chat chat = new Chat();
        chat.setCreator(user);
        chat.setConversation(conv);
        chatRepository.save(chat);
        chat.setFile("http://localhost:8080/files/audio/" + chat.getId());
        chatRepository.save(chat);
        return chat.getId();
    }

    @Transactional
    public void createConversation(Conversation conv, String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        conv.setOwner(user);
        convRepository.save(conv);
    }

    @Transactional
    public Long creatConv(String convName, String username, String description){
        User user = userRepository.findByUsername(username).orElseThrow();
        Conversation conv = new Conversation();
        conv.setOwner(user);
        conv.setName(convName);
        conv.setDescription(description);
        convRepository.save(conv);
        conv.setImage("http://localhost:8080/files/img/conv/" + conv.getId());
        convRepository.save(conv);
        return conv.getId();
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

    @Transactional
    public List<Conversation> findAllConversations(){
        return convRepository.findAll();
    }

    @Transactional
    public String followConversation(long id, Principal principal){
        Conversation conv = convRepository.findById(id).orElseThrow();
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();
        if(conv.getFollowers().add(user)){
            user.getFollowedConversations().add(conv);
            convRepository.save(conv);
            userRepository.save(user);
            return "Conversation followed successfully";
        }
        return "Already following conversation";
    }

    @Transactional
    public List<Conversation> getFollowdConversations(Principal principal){
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();
        return user.getFollowedConversations().stream().collect(Collectors.toList());
    }

    @Transactional
    public List<Conversation> getCreatedConversations(String username){
        User user = userRepository.findByUsername(username).orElseThrow();
        return user.getOwnedConversations().stream().collect(Collectors.toList());
    }

    @Transactional
    public void deleteConversation(long convId){
        Conversation conv = convRepository.findById(convId).orElseThrow();
        chatRepository.deleteInBatch(conv.getChats());
        convRepository.deleteById(convId);
    }

}
