package mscliente.mscliente.RabbitMQConfig;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    private static final String DEFAULT_ROUTING_KEY = "cliente";

    @Bean
    public Queue autocadastroRequests() {
        return new Queue("autocadastro.cliente");
    }

    @Bean
    public DirectExchange sagaAutocadastro() {
        return new DirectExchange("autocadastro");
    }

    @Bean
    public Queue loginClientes() {
        return new Queue("login.cliente");
    }

    @Bean
    public DirectExchange sagaLogin() {
        return new DirectExchange("login");
    }

    @Bean
    public Queue novasReservas() {
        return new Queue("criareserva.cliente");
    }

    @Bean
    public Queue consultaSaldo() {
        return new Queue("criareserva.saldo");
    }

    @Bean
    public DirectExchange sagaCriarReserva() {
        return new DirectExchange("criareserva");
    }

    @Bean
    public Queue cancelarReserva() {
        return new Queue("cancelareserva.cliente");
    }

    @Bean
    public DirectExchange sagaCancelarReserva() {
        return new DirectExchange("cancelareserva");
    }

    @Bean
    public Queue cancelarVoo() {
        return new Queue("cancelavoo.cliente");
    }

    @Bean
    public DirectExchange sagaCancelarVoo() {
        return new DirectExchange("cancelavoo");
    }

    @Bean
    public Binding bindingAutocadastro(DirectExchange sagaAutocadastro, Queue autocadastroRequests) {
        return BindingBuilder.bind(autocadastroRequests)
                .to(sagaAutocadastro)
                .with(DEFAULT_ROUTING_KEY);
    }

    @Bean
    public Binding bindingLogin(DirectExchange sagaLogin, Queue loginClientes) {
        return BindingBuilder.bind(loginClientes)
                .to(sagaLogin)
                .with(DEFAULT_ROUTING_KEY);
    }

    @Bean
    public Binding bindingCriarReserva(DirectExchange sagaCriarReserva, Queue novasReservas) {
        return BindingBuilder.bind(novasReservas)
                .to(sagaCriarReserva)
                .with(DEFAULT_ROUTING_KEY);
    }

    @Bean
    public Binding bindingCancelarReserva(DirectExchange sagaCancelarReserva, Queue cancelarReserva) {
        return BindingBuilder.bind(cancelarReserva)
                .to(sagaCancelarReserva)
                .with(DEFAULT_ROUTING_KEY);
    }

    @Bean
    public Binding bindingConsultaSaldo(DirectExchange sagaCriarReserva, Queue consultaSaldo) {
        return BindingBuilder.bind(consultaSaldo)
                .to(sagaCriarReserva)
                .with("saldo");
    }

    @Bean
    public Binding bindingCancelarVoo(DirectExchange sagaCancelarVoo, Queue cancelarVoo) {
        return BindingBuilder.bind(cancelarVoo)
                .to(sagaCancelarVoo)
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
