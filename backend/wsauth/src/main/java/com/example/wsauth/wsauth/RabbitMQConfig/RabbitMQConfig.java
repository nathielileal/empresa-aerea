package com.example.wsauth.wsauth.RabbitMQConfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue autocadastroRequests() {
        return new Queue("autocadastro.autenticacao");
    }

    @Bean
    public DirectExchange sagaAutocadastro() {
        return new DirectExchange("autocadastro");
    }

    @Bean
    public DirectExchange sagaLogin() {
        return new DirectExchange("login");
    }

    @Bean
    public Queue deactivateFuncionario() {
        return new Queue("deactivate.funcionario");
    }

    @Bean
    public DirectExchange deactivateFuncionarioExchange() {
        return new DirectExchange("deactivate");
    }

    @Bean
    public Binding binding(DirectExchange sagaAutocadastro, Queue autocadastroRequests) {
        return BindingBuilder.bind(autocadastroRequests)
                .to(sagaAutocadastro)
                .with("auth");
    }

    @Bean
    public Binding bindingFuncionario(DirectExchange deactivateFuncionarioExchange, Queue deactivateFuncionario) {
        return BindingBuilder.bind(deactivateFuncionario)
                .to(deactivateFuncionarioExchange)
                .with("funcionario");
    }
}