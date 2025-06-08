package com.ms.voo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class VooDTO {

    private String codigo;
    private LocalDateTime dataHora;
    private String aeroportoOrigem;
    private String aeroportoDestino;
    private BigDecimal valorPassagem;
    private Integer quantidadePoltronas;
    private Integer quantidadeOcupadas;
    private String estado;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getAeroportoOrigem() {
        return aeroportoOrigem;
    }

    public void setAeroportoOrigem(String aeroportoOrigem) {
        this.aeroportoOrigem = aeroportoOrigem;
    }

    public String getAeroportoDestino() {
        return aeroportoDestino;
    }

    public void setAeroportoDestino(String aeroportoDestino) {
        this.aeroportoDestino = aeroportoDestino;
    }

    public BigDecimal getValorPassagem() {
        return valorPassagem;
    }

    public void setValorPassagem(BigDecimal valorPassagem) {
        this.valorPassagem = valorPassagem;
    }

    public Integer getQuantidadePoltronas() {
        return quantidadePoltronas;
    }

    public void setQuantidadePoltronas(Integer quantidadePoltronas) {
        this.quantidadePoltronas = quantidadePoltronas;
    }

    public Integer getQuantidadeOcupadas() {
        return quantidadeOcupadas;
    }

    public void setQuantidadeOcupadas(Integer quantidadeOcupadas) {
        this.quantidadeOcupadas = quantidadeOcupadas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
