package com.invoice.services;

import com.invoice.config.RabbitMQConfig;
import com.invoice.dto.InvoiceErpMessage;
import com.invoice.entity.Invoice;
import com.invoice.entity.InvoiceStatus;
import com.invoice.repositories.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ErpConsumer {

    private final InvoiceRepository repository;
    private final ErpService erpService;

    @RabbitListener(queues = RabbitMQConfig.ERP_QUEUE)
    public void process(InvoiceErpMessage msg) {

        Invoice invoice;
        try {
            invoice = repository.findById(msg.invoiceId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Invoice not found with id " + msg.invoiceId()));
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException(e);
        }

        invoice.setStatus(InvoiceStatus.ERP_PROCESSING);
        repository.save(invoice);

        try {
            erpService.pushInvoice(invoice);
            invoice.setStatus(InvoiceStatus.ERP_COMPLETED);
        } catch (Exception ex) {
            invoice.setStatus(InvoiceStatus.ERP_FAILED);
            repository.save(invoice);
            throw new AmqpRejectAndDontRequeueException(ex);
        }

        repository.save(invoice);
    }
}
