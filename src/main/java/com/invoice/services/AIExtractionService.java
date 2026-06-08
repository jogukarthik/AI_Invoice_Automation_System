package com.invoice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invoice.dto.ClaudeContent;
import com.invoice.dto.ClaudeMessage;
import com.invoice.dto.ClaudeRequest;
import com.invoice.dto.ClaudeResponse;
import com.invoice.dto.ExtractionAIResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AIExtractionService {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    @Value("${anthropic.api-key}")
    private String apiKey;

    @Value("${anthropic.model}")
    private String model;

    public ExtractionAIResult extractInvoiceData(String ocrText) {

        String prompt = """
                Extract the following fields from the invoice OCR text below.
                Return ONLY a valid JSON object with these exact keys:
                invoiceNumber, vendorName, subtotal, tax, total, confidence.
                Do not include any explanation or markdown.

                OCR TEXT:
                """ + ocrText;

        ClaudeRequest request = new ClaudeRequest(
                model,
                1024,
                List.of(new ClaudeMessage("user", prompt))
        );

        String rawResponse = restClient.post()
                .uri("https://api.anthropic.com/v1/messages")
                .header("x-api-key", apiKey)
                .header("anthropic-version", "2023-06-01")
                .body(request)
                .retrieve()
                .body(String.class);

        return parseResponse(rawResponse);
    }

    private ExtractionAIResult parseResponse(String rawResponse) {
        try {
            ClaudeResponse envelope = objectMapper.readValue(rawResponse, ClaudeResponse.class);
            String text = envelope.getContent().get(0).getText();
            return objectMapper.readValue(text, ExtractionAIResult.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Claude response: " + e.getMessage(), e);
        }
    }
}
