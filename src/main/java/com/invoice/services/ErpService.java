package com.invoice.services;

import com.invoice.entity.Invoice;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class ErpService {

    private final RestClient restClient;

    @Value("${erp.base-url}")
    private String erpUrl;
    public void pushInvoice(
            Invoice invoice) {

        restClient.post()
                .uri(
                        erpUrl + "/invoices")
                .body(invoice)
                .retrieve()
                .toBodilessEntity();
    }
}