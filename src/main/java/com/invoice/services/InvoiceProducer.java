package com.invoice.services;

import com.invoice.config.RabbitMQConfig;
import com.invoice.dto.InvoiceProcessMessage;
import com.invoice.exception.InvoiceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceProducer {

    private final RabbitTemplate rabbitTemplate;

    public void publishInvoice(
            Long invoiceId)  {

        rabbitTemplate.convertAndSend(
                RabbitMQConfig
                        .INVOICE_PROCESS_QUEUE,
                new InvoiceProcessMessage(
                        invoiceId)
        );
    }
}
