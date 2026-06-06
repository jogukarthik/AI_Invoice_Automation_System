package com.invoice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Invoice {

    @Id
    @GeneratedValue
    private Long id;

    private String fileName;

    private String sourceEmail;

    private String filePath;

    private String status;

    private LocalDateTime createdAt;
}