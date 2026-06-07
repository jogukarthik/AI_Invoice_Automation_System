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

    @Bean
    public DirectExchange deadLetterExchange() {
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
    public Binding dlqBinding() {
        return BindingBuilder
                .bind(invoiceProcessDlq())
                .to(deadLetterExchange())
                .with(INVOICE_PROCESS_DLQ);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
