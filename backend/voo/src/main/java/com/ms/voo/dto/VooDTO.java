package com.ms.voo.dto;

import java.time.ZonedDateTime;

public class VooDTO {

    private String codigo;
    private ZonedDateTime data;
    private AeroportoDTO aeroporto_origem;
    private AeroportoDTO aeroporto_destino;
    private double valor_passagem;
    private int quantidade_poltronas;
    private int quantidade_ocupadas;
    private VooEstadoDTO estado;

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

    public AeroportoDTO getAeroportoOrigem() {
        return aeroporto_origem;
    }

    public void setAeroportoOrigem(AeroportoDTO aeroporto_origem) {
        this.aeroporto_origem = aeroporto_origem;
    }

    public AeroportoDTO getAeroportoDestino() {
        return aeroporto_destino;
    }

    public void setAeroportoDestino(AeroportoDTO aeroporto_destino) {
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

    public VooEstadoDTO getEstado() {
        return estado;
    }

    public void setEstado(VooEstadoDTO estado) {
        this.estado = estado;
    }
}
