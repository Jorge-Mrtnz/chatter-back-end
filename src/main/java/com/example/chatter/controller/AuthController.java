package com.example.chatter.controller;

import javax.validation.Valid;

import com.example.chatter.model.User;
import com.example.chatter.payload.AuthRequest;
import com.example.chatter.payload.AuthResponse;
import com.example.chatter.repository.UserRepository;
import com.example.chatter.security.jwt.JwtUtil;
import com.example.chatter.security.services.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserDetailsServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest req) {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password");
        }

        final UserDetails user = userService.loadUserByUsername(req.getUsername());

        return ResponseEntity.ok(new AuthResponse(jwtUtil.createToken(user.getUsername())));
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody AuthRequest req) {

        if (userRepository.existsByUsername(req.getUsername())) {
            return new ResponseEntity<String>("Username is already taken", HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(req.getEmail())){
            return new ResponseEntity<String>("Email is already taken", HttpStatus.BAD_REQUEST);
        }

        User user = userService.createUser(
            req.getEmail(),
            req.getUsername(),
            passEncoder.encode(req.getPassword()),
            // false
            true
        );

        userService.saveUser(user);

        // SimpleMailMessage mail = new SimpleMailMessage();
        // mail.setTo(req.getEmail());
        // mail.setSubject("Email Confirmation");
        // mail.setText("http://localhost:8080/auth/confirmEmail?token=" + jwtUtil.createToken(user.getUsername()));

        // mailSender.send(mail);

        // return ResponseEntity.ok("Confirmation Email Sent");
        return ResponseEntity.ok("Registration Successful");
    }

    @GetMapping("confirmEmail")
    public ResponseEntity<?> confirmEmail(@RequestParam String token) {
        User user;
        Jws<Claims> jwt;
        try {
            jwt = jwtUtil.decodeToken(token);
        } catch (JwtException e) {
            return new ResponseEntity<String>("Invalid token", HttpStatus.BAD_REQUEST);
        }

        user = userService.getUser(jwt.getBody().getSubject());

        user.setEnabled(true);
        userService.saveUser(user);

        return ResponseEntity.ok("Email Successfully Validated");
    }

}
