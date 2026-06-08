package com.invoice.controllers;

import com.invoice.dto.ApprovalRequest;
import com.invoice.dto.InvoiceResponse;
import com.invoice.dto.RejectRequest;
import com.invoice.entity.Invoice;
import com.invoice.entity.InvoiceStatus;
import com.invoice.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    @GetMapping
    public List<InvoiceResponse> getInvoices(
            @RequestParam InvoiceStatus status) {

        return reviewService.getInvoices(
                status);
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Void> approve(
            @PathVariable Long id,
            @RequestBody ApprovalRequest request) {

        reviewService.approve(
                id,
                request);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<Void> reject(
            @PathVariable Long id,
            @RequestBody RejectRequest request) {

        reviewService.reject(
                id,
                request.getActor());

        return ResponseEntity.ok().build();
    }
}