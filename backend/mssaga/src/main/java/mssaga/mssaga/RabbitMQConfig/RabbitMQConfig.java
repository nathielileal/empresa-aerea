package mssaga.mssaga.RabbitMQConfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.RemoteInvocationAwareMessageConverterAdapter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setReplyTimeout(60000);
        rabbitTemplate.setMessageConverter(
                new RemoteInvocationAwareMessageConverterAdapter(new SimpleMessageConverter())
        );
        return rabbitTemplate;
    }

    @Bean
    public DirectExchange sagaAutocadastro() {
        return new DirectExchange("autocadastro");
    }

    @Bean
    public DirectExchange sagaCriarReserva() {
        return new DirectExchange("criareserva");
    }

    @Bean
    public DirectExchange sagaCancelarReserva() {
        return new DirectExchange("cancelareserva");
    }

    @Bean
    public DirectExchange sagaCancelarVoo() {
        return new DirectExchange("cancelavoo");
    }

    @Bean
    public DirectExchange sagaRealizarVoo() {
        return new DirectExchange("realizavoo");
    }

    @Bean
    public DirectExchange sagaCadastroVoo() {
        return new DirectExchange("cadastravoo");
    }
}
