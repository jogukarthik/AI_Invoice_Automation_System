package com.invoice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ApprovalRequest {

    private String actor;

    private String vendorName;

    private BigDecimal subtotal;

    private BigDecimal tax;

    private BigDecimal total;
}