package com.invoice.services;

import com.invoice.config.RabbitMQConfig;
import com.invoice.dto.InvoiceProcessMessage;
import com.invoice.entity.Invoice;
import com.invoice.exception.InvoiceNotFoundException;
import com.invoice.exception.OcrfailedException;
import com.invoice.repositories.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InvoiceConsumer {

    private final InvoiceRepository repository;
    private final OcrService ocrService;

    @RabbitListener(queues = RabbitMQConfig.INVOICE_PROCESS_QUEUE)
    public void processInvoice(InvoiceProcessMessage msg) {

        System.out.println("Received Invoice : " + msg.invoiceId());

        Invoice invoice;
        try {
            invoice = repository.findById(msg.invoiceId())
                    .orElseThrow(() -> new InvoiceNotFoundException(
                            "Invoice doesnt exist with id " + msg.invoiceId()));
        } catch (InvoiceNotFoundException e) {
            throw new AmqpRejectAndDontRequeueException(e);
        }

        invoice.setStatus("PROCESSING");
        repository.save(invoice);

        try {
            String text = ocrService.extractText(invoice.getFilePath());
            invoice.setOcrText(text);
            invoice.setStatus("OCR_COMPLETED");
        } catch (OcrfailedException ex) {
            invoice.setStatus("OCR_FAILED");
        }

        repository.save(invoice);
    }
}
