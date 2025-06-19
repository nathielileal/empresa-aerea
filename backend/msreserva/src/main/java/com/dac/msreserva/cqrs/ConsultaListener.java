package com.dac.msreserva.cqrs;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dac.msreserva.model.ReservaConsulta;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class ConsultaListener {

    private final AsyncReservaService service;
    @Autowired
    private ObjectMapper objectMapper;

    public ConsultaListener(AsyncReservaService service) {
        this.service = service;

    }

    @RabbitListener(queues = "cqrs.gravacao")
    public void gravarReserva(String payload) {
        try {
            System.out.println("CQRS escutado");

            ReservaConsulta reserva = objectMapper.readValue(payload, ReservaConsulta.class);
            service.gravarReserva(reserva);

        } catch (Exception e) {
            System.out.println("Erro ao processar mensagem CQRS: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // @RabbitListener(queues = "cqrs.edicao")
    // public void editaReserva(String payload) {
    // ReservaUpdateEstadoDTO reservas = gson.fromJson(payload,
    // ReservaUpdateEstadoDTO.class);
    // service.editarReserva(reservas);
    // }
}
