package com.invoice.controllers;
import com.invoice.dto.InvoiceResponse;
import com.invoice.exception.DuplicateInvoiceException;
import com.invoice.services.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<InvoiceResponse>
    uploadInvoice(

            @RequestParam("file")
            MultipartFile file,

            @RequestParam("sourceEmail")
            String sourceEmail) throws DuplicateInvoiceException {

        InvoiceResponse response =
                invoiceService
                        .uploadInvoice(
                                file,
                                sourceEmail);

        return ResponseEntity.ok(response);
    }
}