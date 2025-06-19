package com.dac.msreserva.cqrs;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dac.msreserva.DTO.ReservaConsultaDTO;
import com.dac.msreserva.model.ReservaConsulta;
import com.dac.msreserva.repository.ConsultaRepository;

@Service
public class AsyncReservaService {

    private final ConsultaRepository repository;

    public AsyncReservaService(ConsultaRepository repository) {
        this.repository = repository;
    }

    public void gravarReserva(ReservaConsulta reserva) {
            repository.save(reserva);
    }

    // public void editarReserva(ReservaUpdateEstadoDTO reserva) {
    //     repository.update(reserva.getEstado(), reserva.getData(), reserva.getCodigo());
    // }
}