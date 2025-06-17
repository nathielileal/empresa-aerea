package com.ms.funcionario.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
public class RabbitMQConfig {

    private static final String DEFAULT_ROUTING_KEY = "funcionario";

    @Bean
    public Queue funcionarioCreateQueue() {
        return new Queue("funcionario.create");
    }

    @Bean
    public Queue funcionarioUpdateQueue() {
        return new Queue("funcionario.update");
    }

    @Bean
    public Queue funcionarioDeleteQueue() {
        return new Queue("funcionario.delete");
    }

    @Bean
    public DirectExchange funcionarioExchange() {
        return new DirectExchange("funcionario.exchange");
    }

    @Bean
    public DirectExchange autocadastroExchange() {
        return new DirectExchange("autocadastro");
    }

    @Bean
    public Binding bindingFuncionarioCreate(DirectExchange funcionarioExchange, Queue funcionarioCreateQueue) {
        return BindingBuilder.bind(funcionarioCreateQueue)
                .to(funcionarioExchange)
                .with(DEFAULT_ROUTING_KEY + ".create");
    }

    @Bean
    public Binding bindingFuncionarioUpdate(DirectExchange funcionarioExchange, Queue funcionarioUpdateQueue) {
        return BindingBuilder.bind(funcionarioUpdateQueue)
                .to(funcionarioExchange)
                .with(DEFAULT_ROUTING_KEY + ".update");
    }

    @Bean
    public Binding bindingFuncionarioDelete(DirectExchange funcionarioExchange, Queue funcionarioDeleteQueue) {
        return BindingBuilder.bind(funcionarioDeleteQueue)
                .to(funcionarioExchange)
                .with(DEFAULT_ROUTING_KEY + ".delete");
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setDefaultRequeueRejected(false);

        return factory;
    }
}
