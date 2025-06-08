package mssaga.mssaga.DTO;

import java.time.ZonedDateTime;

public class TransacaoDTO {
    private Long codigo;
    private String codigo_reserva;
    private ZonedDateTime data;
    private Float quantidade_milhas;
    private Double valor;
    private String descricao;
    private TipoTransacao tipo;

    // Getters e Setters
    public Long getCodigo() {
        return codigo;
    }

    public String getCodigo_reserva() {
        return codigo_reserva;
    }

    public void setCodigo_reserva(String codigo_reserva) {
        this.codigo_reserva = codigo_reserva;
    }


    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
    }

    public Float getQuantidade_milhas() {
        return quantidade_milhas;
    }

    public void setQuantidade_milhas(Float quantidade_milhas) {
        this.quantidade_milhas = quantidade_milhas;
    }
}
