package com.example.pdf_summarizer.controller; // Note: Package name should be lowercase 'controller'
import com.example.pdf_summarizer.model.Summary;
import com.example.pdf_summarizer.repository.SummaryRepository;
import com.example.pdf_summarizer.service.GoogleGeminiService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private GoogleGeminiService googleGeminiService;
    @Autowired
    private SummaryRepository summaryRepository;

    @PostMapping("/summarize")
    public String summarizePdf(@RequestParam("file") MultipartFile file) {
        try {
            // 1. Extract Text (Using Try-With-Resources to auto-close)
            String rawText;
            try (PDDocument document = PDDocument.load(file.getInputStream())) {
                PDFTextStripper pdfStripper = new PDFTextStripper();
                rawText = pdfStripper.getText(document);
            } // document.close() happens automatically here!

            // 2. Truncate text if too long (Gemini has limits)
            if (rawText.length() > 30000) {
                rawText = rawText.substring(0, 30000);
            }

            // 3. Send to AI
            String summary =  googleGeminiService.summarizeText(rawText);
            Summary entity = new Summary();
            entity.setFileName(file.getOriginalFilename());
            entity.setSummaryText(summary);
            summaryRepository.save(entity);

            return summary;

        } catch (IOException e) {
            e.printStackTrace();
            return "Error processing PDF: " + e.getMessage();
        }
    }
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
}
