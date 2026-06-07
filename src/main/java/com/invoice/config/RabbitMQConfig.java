package com.invoice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String
            INVOICE_PROCESS_QUEUE =
            "invoice.process";

    @Bean
    public Queue invoiceProcessQueue() {

        return new Queue(
                INVOICE_PROCESS_QUEUE,
                true
        );
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}