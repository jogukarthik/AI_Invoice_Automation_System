package com.invoice.services;

import com.invoice.config.RabbitMQConfig;
import com.invoice.dto.InvoiceErpMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ErpProducer {

    private final RabbitTemplate rabbitTemplate;

    public void publish(
            Long invoiceId) {

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.ERP_QUEUE,
                new InvoiceErpMessage(
                        invoiceId
                )
        );
    }
}