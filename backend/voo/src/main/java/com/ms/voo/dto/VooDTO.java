package com.ms.voo.dto;

import java.time.ZonedDateTime;

public class VooDTO {

    private String codigo;
    private ZonedDateTime data;
    private String aeroporto_origem;
    private String aeroporto_destino;
    private double valor_passagem;
    private int quantidade_poltronas;
    private int quantidade_ocupadas;
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
        return aeroporto_origem;
    }

    public void setAeroportoOrigem(String aeroporto_origem) {
        this.aeroporto_origem = aeroporto_origem;
    }

    public String getAeroportoDestino() {
        return aeroporto_destino;
    }

    public void setAeroportoDestino(String aeroporto_destino) {
        this.aeroporto_destino = aeroporto_destino;
    }

    public double getValorPassagem() {
        return valor_passagem;
    }

    public void setValorPassagem(double valor_passagem) {
        this.valor_passagem = valor_passagem;
    }

    public int getQuantidadePoltronas() {
        return quantidade_poltronas;
    }

    public void setQuantidadePoltronas(int quantidade_poltronas) {
        this.quantidade_poltronas = quantidade_poltronas;
    }

    public int getQuantidadeOcupadas() {
        return quantidade_ocupadas;
    }

    public void setQuantidadeOcupadas(int quantidade_ocupadas) {
        this.quantidade_ocupadas = quantidade_ocupadas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
