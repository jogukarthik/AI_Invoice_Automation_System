package com.invoice.controllers;

import com.invoice.entity.Invoice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mock-erp")
public class ErpController {

    @PostMapping("/invoices")
    public ResponseEntity<Void>
    receiveInvoice(
            @RequestBody Invoice invoice) {

        System.out.println(
                "ERP RECEIVED : "
                        + invoice.getId());

        return ResponseEntity.ok()
                .build();
    }
}