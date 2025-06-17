package com.ms.voo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;

public class VooDTO {

    private String codigo;
    private ZonedDateTime data;

    @JsonProperty("codigo_aeroporto_origem")
    private String codigo_aeroporto_origem;

    @JsonProperty("codigo_aeroporto_destino")
    private String codigo_aeroporto_destino;

    @JsonProperty("valor_passagem")
    private double valor_passagem;

    @JsonProperty("quantidade_poltronas_total")
    private int quantidade_poltronas_total;

    @JsonProperty("quantidade_poltronas_ocupadas")
    private int quantidade_poltronas_ocupadas;

    private String estado;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getAeroportoOrigem() {
        return codigo_aeroporto_origem;
    }

    public void setAeroportoOrigem(String codigo_aeroporto_origem) {
        this.codigo_aeroporto_origem = codigo_aeroporto_origem;
    }

    public String getAeroportoDestino() {
        return codigo_aeroporto_destino;
    }

    public void setAeroportoDestino(String codigo_aeroporto_destino) {
        this.codigo_aeroporto_destino = codigo_aeroporto_destino;
    }

    public double getValorPassagem() {
        return valor_passagem;
    }

    public void setValorPassagem(double valor_passagem) {
        this.valor_passagem = valor_passagem;
    }

    public int getQuantidadePoltronas() {
        return quantidade_poltronas_total;
    }

    public void setQuantidadePoltronas(int quantidade_poltronas_total) {
        this.quantidade_poltronas_total = quantidade_poltronas_total;
    }

    public int getQuantidadeOcupadas() {
        return quantidade_poltronas_ocupadas;
    }

    public void setQuantidadeOcupadas(int quantidade_poltronas_ocupadas) {
        this.quantidade_poltronas_ocupadas = quantidade_poltronas_ocupadas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
