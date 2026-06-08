package com.invoice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invoice.dto.ClaudeMessage;
import com.invoice.dto.ClaudeRequest;
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

    @Value("${anthropic.api-key}")
    private String apiKey;

    @Value("${anthropic.model}")
    private String model;
    public ExtractionAIResult extractInvoiceData(
            String ocrText) {

        String prompt = """
            Extract:

            invoiceNumber
            vendorName
            subtotal
            tax
            total
            confidence

            Return valid JSON only.

            OCR TEXT:
            """ + ocrText;

        ClaudeRequest request =
                new ClaudeRequest(
                        model,
                        1000,
                        List.of(
                                new ClaudeMessage(
                                        "user",
                                        prompt
                                )
                        )
                );

        String response =
                restClient.post()
                        .uri("https://api.anthropic.com/v1/messages")
                        .header("x-api-key",
                                apiKey)
                        .header("anthropic-version",
                                "2023-06-01")
                        .body(request)
                        .retrieve()
                        .body(String.class);

        System.out.println(response);

        return parseResponse(response);
    }
    private ExtractionAIResult parseResponse(
            String response) {

        ObjectMapper mapper =
                new ObjectMapper();

        try {

            return mapper.readValue(
                    response,
                    ExtractionAIResult.class);

        } catch(Exception e) {

            throw new RuntimeException(e);
        }
    }
}