package com.invoice.dto;

import com.invoice.entity.InvoiceStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceResponse {
    private Long id;
    private InvoiceStatus status;
}
