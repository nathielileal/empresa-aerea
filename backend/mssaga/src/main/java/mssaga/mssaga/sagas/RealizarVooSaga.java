package mssaga.mssaga.sagas;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class RealizarVooSaga {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;
    
     public void realizarVoo(String codigoVoo) {
        try {
            rabbitTemplate.convertAndSend("realizavoo", "voo", codigoVoo);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar solicitação de realização do voo", e);
        }
    }

}
