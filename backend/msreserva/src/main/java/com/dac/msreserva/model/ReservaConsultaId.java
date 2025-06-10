package com.dac.msreserva.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ReservaConsultaId implements Serializable {

    private String codigo;
    private String codigo_voo;

    // Getters and Setters, equals, and hashCode
}
