package com.example.chatter.controller;

import java.security.Principal;
import java.util.List;

import javax.websocket.server.PathParam;

import com.example.chatter.model.Chat;
import com.example.chatter.model.Conversation;
import com.example.chatter.payload.Message;
import com.example.chatter.service.LocalDiskStorageService;
import com.example.chatter.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;


@CrossOrigin
@RestController
@RequestMapping("conversation")
public class ConversationController {

    @Autowired
    private PostService postService;

    @Autowired
    private LocalDiskStorageService storageService;

    @PostMapping("create")
    public ResponseEntity<Message> createConversation(@RequestParam("image") MultipartFile image, @RequestParam("name") String name, @RequestParam("desc") String description, Principal principal){
        try{
            long convId = postService.creatConv(name, principal.getName(), description);
            storageService.saveFile(image, "img/conv/" + convId);
            return ResponseEntity.ok(new Message("Conversation Created Successfully"));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Message("Could create chat"));
        }
    }

    @GetMapping("findByName")
    public List<Conversation> getConversation(@PathParam("name") String name) {
        return postService.getConversationByName(name);
    }


    @GetMapping("chats")
    public List<Chat> getConversationChats(@PathParam("id") long id){
        Conversation conv = postService.findConversationById(id);
        return conv.getChats();
    }

    @GetMapping("follow")
    public ResponseEntity<?> followConversation(@PathParam("id") long id, Principal principal){
        String msg = postService.followConversation(id, principal);
        return ResponseEntity.ok(new Message(msg));
    }

    @GetMapping("followed")
    public List<Conversation> getFollowedConversations(Principal principal){
        return postService.getFollowdConversations(principal);
    }

    @GetMapping("created")
    public List<Conversation> getCreatedConversations(@PathParam("name") String name){
        System.out.println(name);
        return postService.getCreatedConversations(name);
    }

    @DeleteMapping("delete")
    public ResponseEntity<Message> deleteConversation(@PathParam("convId") Long convId){
        System.out.println(convId);
        postService.deleteConversation(convId);
        return ResponseEntity.ok(new Message("Conversation Created Successfully"));
    }
}
