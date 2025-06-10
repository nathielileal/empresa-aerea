package com.dac.msreserva.DTO;

import jakarta.persistence.*;
import java.time.ZonedDateTime;

import com.dac.msreserva.model.EstadoReserva;
import com.dac.msreserva.model.Reserva;

public class HistoricoReservaDTO {

    private Long codigo = 0L;

    private ZonedDateTime data;

    private Reserva reserva;

    private EstadoReserva estadoAntigo;

    private EstadoReserva estadoNovo;

    // Getters and Setters
}
