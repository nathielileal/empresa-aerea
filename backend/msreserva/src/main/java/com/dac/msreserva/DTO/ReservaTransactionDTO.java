package com.dac.msreserva.DTO;

import java.util.List;

public class ReservaTransactionDTO {

    private Double valor;
    private Double milhas_utilizadas;
    private Integer quantidade_poltronas;
    private Long codigo_cliente;
    private VooDTO voo;



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



    public VooDTO getVoo() {
        return voo;
    }



    public void setVoo(VooDTO voo) {
        this.voo = voo;
    }



    public ReservaTransactionDTO(Double valor, Double milhas_utilizadas, Integer quantidade_poltronas,
            List<Integer> poltronas_reservadas, Long codigo_cliente, VooDTO voo) {
        this.valor = valor;
        this.milhas_utilizadas = milhas_utilizadas;
        this.quantidade_poltronas = quantidade_poltronas;
        this.codigo_cliente = codigo_cliente;
        this.voo = voo;
    }

}
