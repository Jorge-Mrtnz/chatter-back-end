package com.example.chatter.controller;

import com.example.chatter.service.LocalDiskStorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;



@CrossOrigin
@RestController
@RequestMapping("files")
public class FileServerController {
    
    @Autowired
    LocalDiskStorageService storageService;

    @GetMapping("audio/{id}")
    @ResponseBody
    public ResponseEntity<Resource> getAudioFile(@PathVariable("id") Long chatId) {
        Resource file = storageService.loadFile("audio/" + chatId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @GetMapping("img/conv/{id}")
    @ResponseBody
    public ResponseEntity<Resource> getImageFile(@PathVariable("id") Long id) {
        Resource file = storageService.loadFile("img/conv/" + id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @GetMapping("img/user/{id}")
    @ResponseBody
    public ResponseEntity<Resource> getPhoto(@PathVariable("id") Long id) {
        Resource file = storageService.loadFile("img/user/" + id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
    
}
