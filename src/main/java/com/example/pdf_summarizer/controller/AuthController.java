package com.example.pdf_summarizer.controller;

import com.example.pdf_summarizer.security.JwtUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam MultiValueMap<String, String> params,
                                        HttpServletRequest request) throws IOException {
        String username = params.getFirst("username");
        String password = params.getFirst("password");

        // If not present in form params and request is JSON, parse JSON body
        String ct = request.getContentType();
        if ((username == null || password == null) && ct != null && ct.contains("application/json")) {
            Map<String, String> body = mapper.readValue(request.getInputStream(), new TypeReference<>() {});
            username = body.get("username");
            password = body.get("password");
        }

        log.info("Login attempt for username='{}' (content-type={})", username, ct);

        if ("vinay".equals(username) && "password".equals(password)) {
            String token = jwtUtil.generateToken(username);
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }
}