package com.example.pdf_summarizer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoogleGeminiService {

    @Value("${gemini.api.key}") // Make sure this matches your application.properties
    private String apiKey;

    // The endpoint for Gemini 1.5 Flash (fast & cost-effective)
    private final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=";
    private final RestTemplate restTemplate = new RestTemplate();

    public String summarizeText(String text) {
        // 1. Setup Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 2. Create the Request Body (The Prompt)
        // Gemini expects: { "contents": [{ "parts": [{ "text": "YOUR PROMPT" }] }] }
        
        String prompt = "Summarize the following text into 3 concise bullet points:\n\n" + text;

        Map<String, String> part = new HashMap<>();
        part.put("text", prompt);

        Map<String, Object> content = new HashMap<>();
        content.put("parts", List.of(part));

        Map<String, Object> body = new HashMap<>();
        body.put("contents", List.of(content));

        // 3. Send the Request
        // Note: Gemini puts the API Key in the URL, not the header
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(GEMINI_URL + apiKey, request, Map.class);

        // 4. Extract the Answer from the JSON Response
        return extractResponse(response.getBody());
    }

    private String extractResponse(Map<String, Object> responseBody) {
        try {
            // Digging through the JSON: candidates -> content -> parts -> text
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) responseBody.get("candidates");
            Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
            return (String) parts.get(0).get("text");
        } catch (Exception e) {
            return "Error parsing Gemini response: " + e.getMessage();
        }
    }
}
