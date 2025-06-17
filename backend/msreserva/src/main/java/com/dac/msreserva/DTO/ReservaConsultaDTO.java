package com.dac.msreserva.DTO;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

import com.dac.msreserva.model.ReservaConsulta;

public class ReservaConsultaDTO {

    private ReservaConsulta codigo;
    private Long codigo_cliente;
    private Long codigo_voo;
    private String estado;

    private ZonedDateTime data;

    private Double quantidade_milhas;



    public ReservaConsulta getCodigo() {
        return codigo;
    }

    public void setCodigo(ReservaConsulta codigo) {
        this.codigo = codigo;
    }

    public Long getCodigo_voo() {
        return codigo_voo;
    }

    public void setCodigo_voo(Long codigo_voo) {
        this.codigo_voo = codigo_voo;
    }

    public Long getCodigo_cliente() {
        return codigo_cliente;
    }

    public void setCodigo_cliente(Long codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public Double getQuantidade_milhas() {
        return quantidade_milhas;
    }

    public void setQuantidade_milhas(Double quantidade_milhas) {
        this.quantidade_milhas = quantidade_milhas;
    }

    // Getters and Setters
}
