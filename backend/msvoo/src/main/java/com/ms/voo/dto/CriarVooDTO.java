package com.ms.voo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;

public class CriarVooDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private ZonedDateTime data;

    @JsonProperty("valor_passagem")
    private double valorPassagem;

    @JsonProperty("quantidade_poltronas_total")
    private int quantidadePoltronasTotal;

    @JsonProperty("quantidade_poltronas_ocupadas")
    private int quantidadePoltronasOcupadas;

    @JsonProperty("codigo_aeroporto_origem")
    private String codigoAeroportoOrigem;

    @JsonProperty("codigo_aeroporto_destino")
    private String codigoAeroportoDestino;

    public ZonedDateTime getData() {
        return data;
    }
    
    public void setData(ZonedDateTime data) {
        this.data = data;
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

    public String getCodigoAeroportoOrigem() {
        return codigoAeroportoOrigem;
    }
    
    public void setCodigoAeroportoOrigem(String codigoAeroportoOrigem) {
        this.codigoAeroportoOrigem = codigoAeroportoOrigem;
    }

    public String getCodigoAeroportoDestino() {
        return codigoAeroportoDestino;
    }
    
    public void setCodigoAeroportoDestino(String codigoAeroportoDestino) {
        this.codigoAeroportoDestino = codigoAeroportoDestino;
    }
}

