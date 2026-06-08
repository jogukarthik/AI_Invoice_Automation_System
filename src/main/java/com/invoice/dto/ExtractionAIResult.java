package com.invoice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtractionAIResult {

    private String invoiceNumber;

    private String vendorName;

    private BigDecimal subtotal;

    private BigDecimal tax;

    private BigDecimal total;

    private Double confidence;
}