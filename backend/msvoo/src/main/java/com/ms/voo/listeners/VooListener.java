package com.ms.voo.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.voo.dto.VooDTO;
import com.ms.voo.services.VooService;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VooListener {

    @Autowired
    private VooService vooService;

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "cancelavoo.reserva")
    public void receberCancelarVoo(String payload) {
        try {
            VooDTO voo = objectMapper.readValue(payload, VooDTO.class);

            vooService.cancelarVoo(voo.getCodigo());
        } catch (Exception e) {
            System.err.println("Erro ao cancelar voo: " + e.getMessage());

            throw new AmqpRejectAndDontRequeueException("Erro no cancelamento do voo", e);
        }
    }

    @RabbitListener(queues = "realizavoo.reserva")
    public void receberRealizarVoo(String payload) {
        try {
            VooDTO voo = objectMapper.readValue(payload, VooDTO.class);

            vooService.realizarVoo(voo.getCodigo());
        } catch (Exception e) {
            System.err.println("Erro ao realizar voo: " + e.getMessage());

            throw new AmqpRejectAndDontRequeueException("Erro na realização do voo", e);
        }
    }

    @RabbitListener(queues = "criareserva.voo")
    public String dadosVoo(String payload) {
        System.out.println("Recebido a mensagem para retornar o voo");
        System.out.println(payload);

        try {
            String input = payload.replace("\"", "");

            VooDTO dadosVoo = vooService.buscarPorCodigo(input);

            System.out.println(dadosVoo);

            return objectMapper.writeValueAsString(dadosVoo);
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException("Erro ao verificar lotação do voo", e);
        }
    }
}
