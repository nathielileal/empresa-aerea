package com.dac.msreserva.model;

import com.dac.msreserva.DTO.EstadoReservaDTO;

import jakarta.persistence.*;

@Entity
@Table(name = "reserva")
public class Reserva {

    @Id
    private String codigo;
    private Long codigo_cliente;
    private String codigo_voo;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estado_codigo")
    private EstadoReserva estado;
    private Integer quantidade_poltronas;

    public Reserva() {
    }

    private Double quantidade_milhas;

    public Reserva(String codigo, Long codigo_cliente, String codigo_voo, EstadoReserva estado,
            Double quantidade_milhas, Integer quantidade_poltronas) {
        this.codigo = codigo;
        this.codigo_cliente = codigo_cliente;
        this.codigo_voo = codigo_voo;
        this.estado = estado;
        this.quantidade_milhas = quantidade_milhas;
        this.quantidade_poltronas = quantidade_poltronas;
    }

    public Reserva(String codigo, Long codigo_cliente, String codigo_voo, EstadoReserva estado,
            Integer quantidade_poltronas, Double quantidade_milhas) {
        this.codigo = codigo;
        this.codigo_cliente = codigo_cliente;
        this.codigo_voo = codigo_voo;
        this.estado = estado;
        this.quantidade_milhas = quantidade_milhas;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Long getCodigo_cliente() {
        return codigo_cliente;
    }

    public void setCodigo_cliente(Long codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }

    public String getCodigo_voo() {
        return codigo_voo;
    }

    public void setCodigo_voo(String codigo_voo) {
        this.codigo_voo = codigo_voo;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    public Double getQuantidade_milhas() {
        return quantidade_milhas;
    }

    public void setQuantidade_milhas(Double quantidade_milhas) {
        this.quantidade_milhas = quantidade_milhas;
    }

    public Integer getQuantidade_poltronas() {
        return quantidade_poltronas;
    }

    public void setQuantidade_poltronas(Integer quantidade_poltronas) {
        this.quantidade_poltronas = quantidade_poltronas;
    }

}
