package com.invoice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClaudeRequest {

    private String model;

    private Integer max_tokens;

    private List<ClaudeMessage> messages;
}
