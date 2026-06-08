package com.invoice.services;

import com.invoice.dto.ApprovalRequest;
import com.invoice.dto.InvoiceResponse;
import com.invoice.entity.Invoice;
import com.invoice.entity.InvoiceStatus;
import com.invoice.repositories.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final InvoiceRepository repository;
    public void approve(
            Long invoiceId,
            ApprovalRequest request) {

        Invoice invoice =
                repository.findById(invoiceId)
                        .orElseThrow();

        if(request.getVendorName() != null) {
            invoice.setVendorName(
                    request.getVendorName());
        }

        if(request.getSubtotal() != null) {
            invoice.setSubtotal(
                    request.getSubtotal());
        }

        if(request.getTax() != null) {
            invoice.setTax(
                    request.getTax());
        }

        if(request.getTotal() != null) {
            invoice.setTotal(
                    request.getTotal());
        }

        invoice.setStatus(
                InvoiceStatus
                        .MANUALLY_APPROVED
        );
        invoice.setReviewedBy(request.getActor());
        invoice.setReviewedAt(LocalDateTime.now());
        repository.save(invoice);
    }

    public void reject(
            Long invoiceId,
            String actor) {

        Invoice invoice =
                repository.findById(invoiceId)
                        .orElseThrow();

        invoice.setStatus(
                InvoiceStatus
                        .REJECTED
                        );
        invoice.setReviewedBy(actor);
        invoice.setReviewedAt(LocalDateTime.now());
        repository.save(invoice);
    }

    public List<InvoiceResponse> getInvoices(InvoiceStatus status){
        List<Invoice> invoices=repository.findByStatus(status);
        List<InvoiceResponse> responses=new ArrayList<>();
        for(Invoice invoice:invoices){
            InvoiceResponse response=new InvoiceResponse();
            response.setId(invoice.getId());
            response.setStatus(invoice.getStatus());
            responses.add(response);
        }
        return responses;
    }
}
