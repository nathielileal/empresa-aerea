package com.dac.msreserva.model;

import jakarta.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "reserva_consulta")
public class ReservaConsulta {

    @Id
    private String codigo;
    private Long codigo_cliente;

    private String estado;

    private ZonedDateTime data;

    private Double quantidade_milhas;

    private String codigo_voo;

    private String aeroporto_origem;

    private Double valor;

    public ReservaConsulta() {
    }

    private String aeroporto_destino;

    public ReservaConsulta(String codigo, Long codigo_cliente, String estado, ZonedDateTime data,
            Double quantidade_milhas, String codigo_voo, String aeroporto_origem, String aeroporto_destino, Double valor) {
        this.codigo = codigo;
        this.codigo_cliente = codigo_cliente;
        this.estado = estado;
        this.data = data;
        this.quantidade_milhas = quantidade_milhas;
        this.codigo_voo = codigo_voo;
        this.aeroporto_origem = aeroporto_origem;
        this.aeroporto_destino = aeroporto_destino;
        this.valor = valor;
    }

    public String getAeroporto_origem() {
        return aeroporto_origem;
    }

    public void setAeroporto_origem(String aeroporto_origem) {
        this.aeroporto_origem = aeroporto_origem;
    }

    public String getAeroporto_destino() {
        return aeroporto_destino;
    }

    public void setAeroporto_destino(String aeroporto_destino) {
        this.aeroporto_destino = aeroporto_destino;
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

    public String getCodigo_voo() {
        return codigo_voo;
    }

    public void setCodigo_voo(String codigo_voo) {
        this.codigo_voo = codigo_voo;
    }

    public Double getValor() {
        return valor;
    }

    // Getters and Setters
}
