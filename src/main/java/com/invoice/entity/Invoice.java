package com.invoice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String sourceEmail;

    private String filePath;

    private String status;

    @Column (unique=true)
    private String fileHash;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String ocrText;

    private LocalDateTime createdAt;
    private String invoiceNumber;

    private String vendorName;

    private BigDecimal subtotal;

    private BigDecimal tax;

    private BigDecimal total;

    private Double confidence;
}