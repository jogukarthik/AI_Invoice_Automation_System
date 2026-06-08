package com.invoice.dto.enums;

public enum InvoiceStatus {

    RECEIVED,

    PROCESSING,

    OCR_RUNNING,

    OCR_COMPLETED,

    EXTRACTION_RUNNING,

    EXTRACTION_COMPLETED,

    EXTRACTION_FAILED,

    AUTO_APPROVED,

    NEEDS_REVIEW,

    MANUALLY_APPROVED,

    REJECTED
}