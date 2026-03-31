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

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public EstadoReserva getEstadoAntigo() {
        return estadoAntigo;
    }

    public void setEstadoAntigo(EstadoReserva estadoAntigo) {
        this.estadoAntigo = estadoAntigo;
    }

    public EstadoReserva getEstadoNovo() {
        return estadoNovo;
    }

    public void setEstadoNovo(EstadoReserva estadoNovo) {
        this.estadoNovo = estadoNovo;
    }

    // Getters and Setters
}
