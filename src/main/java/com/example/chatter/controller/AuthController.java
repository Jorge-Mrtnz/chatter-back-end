package com.example.chatter.controller;

import com.example.chatter.config.JwtUtil;
import com.example.chatter.model.AuthRequest;
import com.example.chatter.model.AuthResponse;
import com.example.chatter.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
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
    private JdbcUserDetailsManager userDetailsManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password");
        }

        final UserDetails user = userDetailsManager.loadUserByUsername(req.getUsername());

        return ResponseEntity.ok(new AuthResponse(jwtUtil.createToken(user)));
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody AuthRequest req) {

        if (userDetailsManager.userExists(req.getUsername())) {
            return new ResponseEntity<String>("User already exists.", HttpStatus.BAD_REQUEST);
        }

        // userService.createUser(req);

        UserDetails user = User.builder().username(req.getUsername()).password(passEncoder.encode(req.getPassword()))
                .authorities("ROLE_user").disabled(true).build();
        userDetailsManager.createUser(user);

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(req.getUsername());
        mail.setSubject("Email Confirmation");
        mail.setText("http://localhost:8080/auth/confirmEmail?token=" + jwtUtil.createToken(user));

        mailSender.send(mail);

        return ResponseEntity.ok("Ok");
    }

    @GetMapping("confirmEmail")
    public ResponseEntity<?> confirmEmail(@RequestParam String token) {
        UserDetails disabledUser;
        UserDetails enabledUser;
        Jws<Claims> jwt;
        try {
            jwt = jwtUtil.decodeToken(token);
        } catch (JwtException e) {
            return new ResponseEntity<String>("Invalid token", HttpStatus.BAD_REQUEST);
        }
        try {
            disabledUser = userDetailsManager.loadUserByUsername(jwt.getBody().getSubject());
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<String>("Invalid user", HttpStatus.BAD_REQUEST);
        }
        enabledUser = User.builder().username(disabledUser.getUsername()).password(disabledUser.getPassword())
                .authorities(disabledUser.getAuthorities()).disabled(false).build();
        userDetailsManager.updateUser(enabledUser);
        return ResponseEntity.ok("Ok");
    }

}
