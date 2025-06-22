package com.ms.voo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;

public class VooDTO {

    private String codigo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private ZonedDateTime data;

    @JsonProperty("aeroporto_origem")
    private AeroportoDTO aeroportoOrigem;

    @JsonProperty("aeroporto_destino")
    private AeroportoDTO aeroportoDestino;

    @JsonProperty("valor_passagem")
    private double valorPassagem;

    @JsonProperty("quantidade_poltronas_total")
    private int quantidadePoltronasTotal;

    @JsonProperty("quantidade_poltronas_ocupadas")
    private int quantidadePoltronasOcupadas;

    private String estado;

    public VooDTO() {
    }

    public VooDTO(String codigo, ZonedDateTime data, AeroportoDTO aeroportoOrigem, AeroportoDTO aeroportoDestino,
            double valorPassagem, int quantidadePoltronasTotal, int quantidadePoltronasOcupadas, String estado) {
        this.codigo = codigo;
        this.data = data;
        this.aeroportoOrigem = aeroportoOrigem;
        this.aeroportoDestino = aeroportoDestino;
        this.valorPassagem = valorPassagem;
        this.quantidadePoltronasTotal = quantidadePoltronasTotal;
        this.quantidadePoltronasOcupadas = quantidadePoltronasOcupadas;
        this.estado = estado;
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

    public AeroportoDTO getAeroportoOrigem() {
        return aeroportoOrigem;
    }

    public void setAeroportoOrigem(AeroportoDTO aeroportoOrigem) {
        this.aeroportoOrigem = aeroportoOrigem;
    }

    public AeroportoDTO getAeroportoDestino() {
        return aeroportoDestino;
    }

    public void setAeroportoDestino(AeroportoDTO aeroportoDestino) {
        this.aeroportoDestino = aeroportoDestino;
    }

    public double getValorPassagem() {
        return valorPassagem;
    }

    public void setValorPassagem(double valorPassagem) {
        this.valorPassagem = valorPassagem;
    }

    public int getQuantidadePoltronasTotal() {
        return quantidadePoltronasTotal;
    }

    public void setQuantidadePoltronasTotal(int quantidadePoltronasTotal) {
        this.quantidadePoltronasTotal = quantidadePoltronasTotal;
    }

    public int getQuantidadePoltronasOcupadas() {
        return quantidadePoltronasOcupadas;
    }

    public void setQuantidadePoltronasOcupadas(int quantidadePoltronasOcupadas) {
        this.quantidadePoltronasOcupadas = quantidadePoltronasOcupadas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
