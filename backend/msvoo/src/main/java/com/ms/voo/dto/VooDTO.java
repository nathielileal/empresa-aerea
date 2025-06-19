package com.ms.voo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;

public class VooDTO {

    private String codigo;
    private ZonedDateTime data;

    @JsonProperty("aeroporto_origem")
    private AeroportoDTO aeroporto_origem;
    @JsonProperty("aeroporto_destino")
    private AeroportoDTO aeroporto_destino;

    @JsonProperty("valor_passagem")
    private double valor_passagem;

    public AeroportoDTO getAeroporto_origem() {
        return aeroporto_origem;
    }

    public void setAeroporto_origem(AeroportoDTO aeroporto_origem) {
        this.aeroporto_origem = aeroporto_origem;
    }

    public AeroportoDTO getAeroporto_destino() {
        return aeroporto_destino;
    }

    public void setAeroporto_destino(AeroportoDTO aeroporto_destino) {
        this.aeroporto_destino = aeroporto_destino;
    }

    public VooDTO() {
    }

    @JsonProperty("quantidade_poltronas_total")
    private int quantidade_poltronas_total;

    @JsonProperty("quantidade_poltronas_ocupadas")
    private int quantidade_poltronas_ocupadas;

    private String estado;



    public double getValor_passagem() {
        return valor_passagem;
    }

    public void setValor_passagem(double valor_passagem) {
        this.valor_passagem = valor_passagem;
    }

    public int getQuantidade_poltronas_total() {
        return quantidade_poltronas_total;
    }

    public void setQuantidade_poltronas_total(int quantidade_poltronas_total) {
        this.quantidade_poltronas_total = quantidade_poltronas_total;
    }

    public int getQuantidade_poltronas_ocupadas() {
        return quantidade_poltronas_ocupadas;
    }

    public void setQuantidade_poltronas_ocupadas(int quantidade_poltronas_ocupadas) {
        this.quantidade_poltronas_ocupadas = quantidade_poltronas_ocupadas;
    }

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

    public VooDTO(String codigo, ZonedDateTime data, AeroportoDTO aeroporto_origem, AeroportoDTO aeroporto_destino,
            double valor_passagem, int quantidade_poltronas_total, int quantidade_poltronas_ocupadas, String estado) {
        this.codigo = codigo;
        this.data = data;
        this.aeroporto_origem = aeroporto_origem;
        this.aeroporto_destino = aeroporto_destino;
        this.valor_passagem = valor_passagem;
        this.quantidade_poltronas_total = quantidade_poltronas_total;
        this.quantidade_poltronas_ocupadas = quantidade_poltronas_ocupadas;
        this.estado = estado;
    }
}
