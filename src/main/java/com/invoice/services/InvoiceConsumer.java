package com.invoice.services;

import com.invoice.config.RabbitMQConfig;
import com.invoice.dto.InvoiceProcessMessage;
import com.invoice.entity.Invoice;
import com.invoice.exception.InvoiceNotFoundException;
import com.invoice.repositories.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InvoiceConsumer {

    private final InvoiceRepository repository;

    @RabbitListener(
            queues =
                    RabbitMQConfig
                            .INVOICE_PROCESS_QUEUE
    )
    public void processInvoice (
            InvoiceProcessMessage msg) throws InvoiceNotFoundException {

        System.out.println(
                "Received Invoice : "
                        + msg.invoiceId()
        );

        Invoice invoice =
                repository.findById(
                                msg.invoiceId())
                        .orElseThrow(()->new InvoiceNotFoundException("Invoice doesnt exist with id"+msg.invoiceId()));

        invoice.setStatus(
                "PROCESSING"
        );

        repository.save(invoice);
    }
}