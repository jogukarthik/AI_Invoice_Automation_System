package com.invoice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String INVOICE_PROCESS_QUEUE = "invoice.process";
    public static final String INVOICE_PROCESS_DLQ   = "invoice.process.dlq";
    public static final String INVOICE_PROCESS_DLX   = "invoice.process.dlx";

    public static final String ERP_QUEUE = "invoice.erp";
    public static final String ERP_DLQ   = "invoice.erp.dlq";
    public static final String ERP_DLX   = "invoice.erp.dlx";

    // --- Invoice Process queue ---

    @Bean
    public DirectExchange invoiceDeadLetterExchange() {
        return new DirectExchange(INVOICE_PROCESS_DLX);
    }

    @Bean
    public Queue invoiceProcessQueue() {
        return QueueBuilder.durable(INVOICE_PROCESS_QUEUE)
                .withArgument("x-dead-letter-exchange", INVOICE_PROCESS_DLX)
                .withArgument("x-dead-letter-routing-key", INVOICE_PROCESS_DLQ)
                .build();
    }

    @Bean
    public Queue invoiceProcessDlq() {
        return QueueBuilder.durable(INVOICE_PROCESS_DLQ).build();
    }

    @Bean
    public Binding invoiceDlqBinding() {
        return BindingBuilder
                .bind(invoiceProcessDlq())
                .to(invoiceDeadLetterExchange())
                .with(INVOICE_PROCESS_DLQ);
    }

    // --- ERP queue ---

    @Bean
    public DirectExchange erpDeadLetterExchange() {
        return new DirectExchange(ERP_DLX);
    }

    @Bean
    public Queue erpQueue() {
        return QueueBuilder.durable(ERP_QUEUE)
                .withArgument("x-dead-letter-exchange", ERP_DLX)
                .withArgument("x-dead-letter-routing-key", ERP_DLQ)
                .build();
    }

    @Bean
    public Queue erpDlq() {
        return QueueBuilder.durable(ERP_DLQ).build();
    }

    @Bean
    public Binding erpDlqBinding() {
        return BindingBuilder
                .bind(erpDlq())
                .to(erpDeadLetterExchange())
                .with(ERP_DLQ);
    }

    // --- Shared ---

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
