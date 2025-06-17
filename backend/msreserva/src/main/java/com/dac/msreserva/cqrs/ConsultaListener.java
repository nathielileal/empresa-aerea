package com.dac.msreserva.cqrs;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dac.msreserva.DTO.ReservaConsultaDTO;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class ConsultaListener {

    private final AsyncReservaService service;
    @Autowired
    private ModelMapper mapper;

    public ConsultaListener(AsyncReservaService service) {
        this.service = service;

    }

    @RabbitListener(queues = "cqrs.gravacao")
    public void gravarReserva(String payload) {
        Type listType = new TypeToken<List<ReservaConsultaDTO>>() {
        }.getType();
        List<ReservaConsultaDTO> reservas = mapper.map(payload, listType);
        service.gravarReserva(reservas);
    }

    // @RabbitListener(queues = "cqrs.edicao")
    // public void editaReserva(String payload) {
    //     ReservaUpdateEstadoDTO reservas = gson.fromJson(payload, ReservaUpdateEstadoDTO.class);
    //     service.editarReserva(reservas);
    // }
}
