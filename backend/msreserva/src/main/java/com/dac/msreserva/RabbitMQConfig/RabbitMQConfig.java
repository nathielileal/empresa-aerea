package com.dac.msreserva.RabbitMQConfig;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private static final String DEFAULT_ROUTING_KEY = "reserva";

    @Bean
    public Queue novasReservas() {
        return new Queue("criareserva.reserva");
    }

    @Bean
    public DirectExchange sagaCriarReserva() {
        return new DirectExchange("criareserva");
    }

    @Bean
    public Queue reservasCQRSgravacao() {
        return new Queue("cqrs.gravacao");
    }

    @Bean
    public Queue reservasCQRSedicao() {
        return new Queue("cqrs.edicao");
    }

    @Bean
    public DirectExchange reservasCQRSExchange() {
        return new DirectExchange("cqrs");
    }

    @Bean
    public Queue cancelarReserva() {
        return new Queue("cancelareserva.reserva");
    }

    @Bean
    public DirectExchange sagaCancelarReserva() {
        return new DirectExchange("cancelareserva");
    }

    @Bean
    public Queue cancelarVoo() {
        return new Queue("cancelavoo.reserva");
    }

    @Bean
    public DirectExchange sagaCancelarVoo() {
        return new DirectExchange("cancelavoo");
    }

    @Bean
    public Queue realizarVoo() {
        return new Queue("realizavoo.reserva");
    }

    @Bean
    public DirectExchange sagaRealizarVoo() {
        return new DirectExchange("realizavoo");
    }

    @Bean
    public Binding bindingCriarReserva(DirectExchange sagaCriarReserva, Queue novasReservas) {
        return BindingBuilder.bind(novasReservas)
                .to(sagaCriarReserva)
                .with(DEFAULT_ROUTING_KEY);
    }

    @Bean
    public Binding bindingCQRSReservaGravacao(DirectExchange reservasCQRSExchange, Queue reservasCQRSgravacao) {
        return BindingBuilder.bind(reservasCQRSgravacao)
                .to(reservasCQRSExchange)
                .with("gravacao");
    }

    @Bean
    public Binding bindingCQRSReservaEdicao(DirectExchange reservasCQRSExchange, Queue reservasCQRSedicao) {
        return BindingBuilder.bind(reservasCQRSedicao)
                .to(reservasCQRSExchange)
                .with("edicao");
    }

    @Bean
    public Binding bindingCancelarReserva(DirectExchange sagaCancelarReserva, Queue cancelarReserva) {
        return BindingBuilder.bind(cancelarReserva)
                .to(sagaCancelarReserva)
                .with(DEFAULT_ROUTING_KEY);
    }

    @Bean
    public Binding bindingCancelarVoo(DirectExchange sagaCancelarVoo, Queue cancelarVoo) {
        return BindingBuilder.bind(cancelarVoo)
                .to(sagaCancelarVoo)
                .with(DEFAULT_ROUTING_KEY);
    }

    @Bean
    public Binding bindingRealizarVoo(DirectExchange sagaRealizarVoo, Queue realizarVoo) {
        return BindingBuilder.bind(realizarVoo)
                .to(sagaRealizarVoo)
                .with(DEFAULT_ROUTING_KEY);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setDefaultRequeueRejected(false);
        return factory;
    }
}
