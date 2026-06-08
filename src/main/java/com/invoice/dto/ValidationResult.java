package com.invoice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationResult {

    private boolean arithmeticValid;

    private boolean confidenceValid;

    private boolean autoApproved;
}