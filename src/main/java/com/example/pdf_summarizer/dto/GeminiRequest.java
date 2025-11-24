package com.example.pdf_summarizer.dto;

import java.util.List;

// 1. The Root Object ("contents": [...])
public class GeminiRequest {
    private List<Content> contents;

    public GeminiRequest(String promptText) {
        // Automatically build the nested structure
        this.contents = List.of(new Content(promptText));
    }

    public List<Content> getContents() { return contents; }

    // 2. The Content Object ("parts": [...])
    public static class Content {
        private List<Part> parts;

        public Content(String text) {
            this.parts = List.of(new Part(text));
        }

        public List<Part> getParts() { return parts; }
    }

    // 3. The Part Object ("text": "...")
    public static class Part {
        private String text;

        public Part(String text) {
            this.text = text;
        }

        public String getText() { return text; }
    }
}
