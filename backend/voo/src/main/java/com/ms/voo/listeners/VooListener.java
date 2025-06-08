package com.ms.voo.listeners;

import com.ms.voo.services.VooService;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VooListener {

    @Autowired
    private VooService vooService;

    @RabbitListener(queues = "voo.cancel")
    public void receberCancelarVoo(String codigoVoo) {
        try {
            vooService.cancelarVoo(codigoVoo);
        } catch (Exception e) {
            System.err.println("Erro ao cancelar voo: " + e.getMessage());

            throw new AmqpRejectAndDontRequeueException("Erro no cancelamento do voo", e);
        }
    }

    @RabbitListener(queues = "voo.realize")
    public void receberRealizarVoo(String codigoVoo) {
        try {
            vooService.realizarVoo(codigoVoo);
        } catch (Exception e) {
            System.err.println("Erro ao realizar voo: " + e.getMessage());

            throw new AmqpRejectAndDontRequeueException("Erro na realização do voo", e);
        }
    }
}
