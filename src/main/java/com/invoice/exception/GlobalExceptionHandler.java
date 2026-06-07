package com.invoice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            DuplicateInvoiceException.class)
    public ResponseEntity<String>
    handleDuplicate(
            DuplicateInvoiceException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    @ExceptionHandler(
            InvoiceNotFoundException.class)
    public ResponseEntity<String>
    handleInvoiceNotFound(
            InvoiceNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
    @ExceptionHandler(
            OcrfailedException.class)
    public ResponseEntity<String>
    handleOcrException(
            OcrfailedException ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }
}