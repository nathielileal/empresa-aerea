package com.ms.voo.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
public class RabbitMQConfig {

    private static final String DEFAULT_ROUTING_KEY = "voo";

    @Bean
    public Queue vooCancelQueue() {
        return new Queue("voo.cancel");
    }

    @Bean
    public Queue vooRealizeQueue() {
        return new Queue("voo.realize");
    }

    @Bean
    public DirectExchange vooExchange() {
        return new DirectExchange("voo.exchange");
    }

    @Bean
    public Binding bindingVooCancel(DirectExchange vooExchange, Queue vooCancelQueue) {
        return BindingBuilder.bind(vooCancelQueue)
                .to(vooExchange)
                .with(DEFAULT_ROUTING_KEY + ".cancel");
    }

    @Bean
    public Binding bindingVooRealize(DirectExchange vooExchange, Queue vooRealizeQueue) {
        return BindingBuilder.bind(vooRealizeQueue)
                .to(vooExchange)
                .with(DEFAULT_ROUTING_KEY + ".realize");
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setDefaultRequeueRejected(false);

        return factory;
    }
}
