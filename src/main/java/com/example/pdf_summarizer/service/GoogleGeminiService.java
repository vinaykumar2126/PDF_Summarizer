package com.example.pdf_summarizer.service;

import com.example.pdf_summarizer.dto.GeminiRequest;
import com.example.pdf_summarizer.dto.GeminiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleGeminiService {

    @Value("${gemini.api.key}")   // Inject API Key from application.properties to apiKey field.
    private String apiKey;

    // Using the latest stable model
    private final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=";
    private final RestTemplate restTemplate = new RestTemplate();

    public String summarizeText(String text) {
        // 1. Setup Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 2. Build the Request Object (Using our DTO)
        // This ONE line replaces all those nested HashMaps!
        String prompt = "Summarize the following text into 3 concise bullet points:\n\n" + text;
        GeminiRequest requestBody = new GeminiRequest(prompt);

        // 3. Create the HTTP Entity
        HttpEntity<GeminiRequest> request = new HttpEntity<>(requestBody, headers);

        try {
            // 4. Send Request & Get Response (Using our DTO)
            // Jackson automatically converts the JSON response into our GeminiResponse object
            GeminiResponse response = restTemplate.postForObject(GEMINI_URL + apiKey, request, GeminiResponse.class);
            
            // 5. Extract the text
            return extractTextFromResponse(response);

        } catch (Exception e) {
            e.printStackTrace();
            return "Error calling Gemini API: " + e.getMessage();
        }
    }

    // Helper method to extract text using DTO Getters (Type-Safe)
    private String extractTextFromResponse(GeminiResponse response) {
        // Safety checks using proper Java objects
        if (response == null || response.getCandidates() == null || response.getCandidates().isEmpty()) {
            return "Error: No response from AI.";
        }
        
        // Clean chain of getters instead of messy map.get("key") casting
        return response.getCandidates().get(0)
                .getContent()
                .getParts().get(0)
                .getText();
    }
}