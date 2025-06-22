package com.dac.msreserva.listeners;

import java.time.ZonedDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.dac.msreserva.DTO.ReservaTransactionDTO;
import com.dac.msreserva.DTO.VooDTO;
import com.dac.msreserva.services.ReservaService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
            System.out.println("Retorno");
            System.out.println(resultado);
            return objectMapper.writeValueAsString(resultado);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar reserva", e);
        }
    }

    @RabbitListener(queues = "cancelareserva.reserva")
    public String cancelarReserva(String payload) {
        try {
            System.out.println("Iniciando cancelamento da reserva");

            String codigo = objectMapper.readValue(payload, String.class);
            System.out.println("Código recebido: " + codigo);

            Object reserva = service.cancelarReserva(codigo);
            return objectMapper.writeValueAsString(reserva);

        } catch (ResponseStatusException e) {
            return "{\"erro\": \"" + e.getReason() + "\", \"status\": " + e.getStatusCode().value() + "}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"erro\": \"Erro inesperado ao cancelar reserva\"}";
        }
    }

}

// @RabbitListener(queues = "realizavoo.reserva", errorHandler =
// "customErrorHandler")
// public String realizaVoo(String payload) {
// VooDTO voo = gson.fromJson(payload, VooDTO.class);
// Object reserva = service.realizaVoo(voo);
// response = new (true, reserva);

// return gson.toJson(response);
// }
// }