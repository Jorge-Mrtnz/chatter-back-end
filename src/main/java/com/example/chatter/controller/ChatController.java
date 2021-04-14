package com.example.chatter.controller;

import java.security.Principal;

import com.example.chatter.payload.Message;
import com.example.chatter.service.LocalDiskStorageService;
import com.example.chatter.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("chat")
public class ChatController {

    @Autowired
    private PostService postService;

    @Autowired
    private LocalDiskStorageService storageService;

    @PostMapping("create")
    public ResponseEntity<Message> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("convId") Long convId, Principal principal) {
        try {
            long chatId = postService.createChat(convId, principal);
            storageService.saveFile(file, "audio/" + chatId);
            return ResponseEntity.ok(new Message("Chat created successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Message("Could not create chat"));
        }
    }

    @GetMapping("get")
    public ResponseEntity<?> getChat(@RequestParam("chatId") Long chatId) {
        return ResponseEntity.ok(postService.findChatById(chatId));
    }

}
