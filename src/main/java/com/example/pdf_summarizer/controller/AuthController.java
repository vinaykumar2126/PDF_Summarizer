package com.example.pdf_summarizer.controller;

import com.example.pdf_summarizer.security.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    // private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> body) throws IOException {
        String username = body.get("username");
        String password = body.get("password");

        Map<String,String> users = Map.of(
            "Vinay", "password",
            "Alice", "alice123"
        );

        log.info("Login attempt for username='{}' (content-type={})", username);

        if (users.containsKey(username) && users.get(username).equals(password)) {
            System.out.println("User '" + username + "' logged in successfully.");
            String token = jwtUtil.generateToken(username);
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }
}