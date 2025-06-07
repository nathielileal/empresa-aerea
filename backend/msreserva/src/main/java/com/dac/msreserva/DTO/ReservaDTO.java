package com.dac.msreserva.DTO;

import java.util.List;

public class ReservaDTO {
    private Double valor;
    private Double milhas_utilizadas;
    private Integer quantidade_poltronas;
    private Long codigo_cliente;
    private String codigo_voo;

    public ReservaDTO() {
    }

    public ReservaDTO(Double valor, Double milhas_utilizadas, Integer quantidade_poltronas, Long codigo_cliente,
            String codigo_voo) {
        this.valor = valor;
        this.milhas_utilizadas = milhas_utilizadas;
        this.quantidade_poltronas = quantidade_poltronas;
        this.codigo_cliente = codigo_cliente;
        this.codigo_voo = codigo_voo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getMilhas_utilizadas() {
        return milhas_utilizadas;
    }

    public void setMilhas_utilizadas(Double milhas_utilizadas) {
        this.milhas_utilizadas = milhas_utilizadas;
    }

    public Integer getQuantidade_poltronas() {
        return quantidade_poltronas;
    }

    public void setQuantidade_poltronas(Integer quantidade_poltronas) {
        this.quantidade_poltronas = quantidade_poltronas;
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

}
