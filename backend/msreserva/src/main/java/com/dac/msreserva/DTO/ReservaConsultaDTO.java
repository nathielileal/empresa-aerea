package com.dac.msreserva.DTO;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

import com.dac.msreserva.model.ReservaConsulta;

public class ReservaConsultaDTO {

    private ReservaConsulta id;
    private List<Integer> poltronas;
    private Long codigo_cliente;
    private String estado;

    private ZonedDateTime data;

    private Double quantidade_milhas;

    // Getters and Setters
}
