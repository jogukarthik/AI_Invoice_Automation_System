package com.invoice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationResult {

    private boolean arithmeticValid;

    private boolean confidenceValid;

    private boolean autoApproved;
}