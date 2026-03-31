package mssaga.mssaga.sagas;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CancelarVooSaga {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void cancelarVoo(String codigoVoo) {
        try {
            rabbitTemplate.convertSendAndReceive("cancelavoo", "reserva", codigoVoo);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar solicitação de cancelamento do voo", e);
        }
    } 
}
