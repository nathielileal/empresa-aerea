package com.dac.msreserva.DTO;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VooDTO {

    private String codigo;
    private ZonedDateTime data;
    private Double valor_passagem;
    private String estado;
    @JsonProperty("aeroporto_origem")
    private AeroportoDTO aeroporto_origem;
    @JsonProperty("aeroporto_destino")
    private AeroportoDTO aeroporto_destino;
    public String getCodigo() {
        return codigo;
    }
    public VooDTO(String codigo, AeroportoDTO aeroporto_origem, AeroportoDTO aeroporto_destino) {
        this.codigo = codigo;
        this.aeroporto_origem = aeroporto_origem;
        this.aeroporto_destino = aeroporto_destino;
    }
    public VooDTO(String codigo) {
        this.codigo = codigo;
    }
    public VooDTO() {
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
    public Double getValor_passagem() {
        return valor_passagem;
    }
    public void setValor_passagem(Double valor_passagem) {
        this.valor_passagem = valor_passagem;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
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

}
