package com.dac.msreserva.model;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "reserva", schema = "reserva_access")
public class ReservaConsulta {

    @EmbeddedId
    private ReservaConsultaId id;

    @Column(name = "poltronas", columnDefinition = "integer[]")
    private List<Integer> poltronas;

    private Long codigo_cliente;

    private String estado;

    private ZonedDateTime data;

    private Double quantidade_milhas;

    // Getters and Setters
}
