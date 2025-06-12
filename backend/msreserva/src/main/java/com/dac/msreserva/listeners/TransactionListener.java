package com.dac.msreserva.listeners;

import java.time.ZonedDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dac.msreserva.DTO.ReservaTransactionDTO;
import com.dac.msreserva.DTO.VooDTO;
import com.dac.msreserva.services.ReservaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TransactionListener {

    private final ReservaService service;
    @Autowired
    private ObjectMapper objectMapper;

    public TransactionListener(ReservaService service) {
        this.service = service;
    }

    @RabbitListener(queues = "criareserva.reserva")
    public String efetuarReserva(String payload) {
        try {
            System.out.println("Criar reservada escutado");
            System.out.println(payload);
            ReservaTransactionDTO reservaTransaction = objectMapper.readValue(payload, ReservaTransactionDTO.class);

            Object resultado = service.efetuarReserva(reservaTransaction);

            return objectMapper.writeValueAsString(resultado);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar reserva", e);
        }
    }
}

    // @RabbitListener(queues = "cancelareserva.reserva", errorHandler =
    // "customErrorHandler")
    // public String cancelarReserva(String codigo) {
    // Object reserva = service.cancelarReserva(codigo);
    // response = new (true, reserva);

    // return gson.toJson(response);
    // }

    // @RabbitListener(queues = "cancelavoo.reserva", errorHandler =
    // "customErrorHandler")
    // public String canceladoVoo(String payload) {
    // VooDTO voo = gson.fromJson(payload, VooDTO.class);
    // Object reserva = service.canceladoVoo(voo);
    // response = new (true, reserva);

    // return gson.toJson(response);
    // }

    // @RabbitListener(queues = "realizavoo.reserva", errorHandler =
    // "customErrorHandler")
    // public String realizaVoo(String payload) {
    // VooDTO voo = gson.fromJson(payload, VooDTO.class);
    // Object reserva = service.realizaVoo(voo);
    // response = new (true, reserva);

    // return gson.toJson(response);
    // }
// }