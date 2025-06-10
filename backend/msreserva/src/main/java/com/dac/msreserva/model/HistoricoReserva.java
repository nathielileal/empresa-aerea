package com.dac.msreserva.model;

import jakarta.persistence.*;
import java.time.ZonedDateTime;

import com.dac.msreserva.DTO.EstadoReservaDTO;

@Entity
@Table(name = "historico_reserva", schema = "reserva_transaction")
public class HistoricoReserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo = 0L;

    private ZonedDateTime data;

    @ManyToOne
    @JoinColumn(name = "reserva_codigo")
    private Reserva reserva;

    @OneToOne
    @JoinColumn(name = "estado_old")
    private EstadoReserva estadoAntigo;

    @OneToOne
    @JoinColumn(name = "estado_new")
    private EstadoReserva estadoNovo;

    public Long getCodigo() {
        return codigo;
    }

    public HistoricoReserva(Long codigo, ZonedDateTime data, Reserva reserva, EstadoReserva estadoAntigo,
            EstadoReserva estadoNovo) {
        this.codigo = codigo;
        this.data = data;
        this.reserva = reserva;
        this.estadoAntigo = estadoAntigo;
        this.estadoNovo = estadoNovo;
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

}
