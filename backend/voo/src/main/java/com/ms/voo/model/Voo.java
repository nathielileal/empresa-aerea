package com.ms.voo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "voo")
public class Voo {

    @Id
    private String codigo;

    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "aeroporto_origem")
    private Aeroporto aeroportoOrigem;

    @ManyToOne
    @JoinColumn(name = "aeroporto_destino")
    private Aeroporto aeroportoDestino;

    private BigDecimal valorPassagem;

    private Integer quantidadePoltronas;

    private Integer quantidadeOcupadas;

    @ManyToOne
    @JoinColumn(name = "estado_id")
    private VooEstado estado;

    public Voo() {
    }

    public Voo(LocalDateTime dataHora, Aeroporto origem, Aeroporto destino) {
        this.dataHora = dataHora;
        this.aeroportoOrigem = origem;
        this.aeroportoDestino = destino;
        this.quantidadePoltronas = 100;
        this.quantidadeOcupadas = 0;
        this.valorPassagem = new BigDecimal("0.00"); 
    }

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

    public Aeroporto getAeroportoOrigem() {
        return aeroportoOrigem;
    }

    public void setAeroportoOrigem(Aeroporto aeroportoOrigem) {
        this.aeroportoOrigem = aeroportoOrigem;
    }

    public Aeroporto getAeroportoDestino() {
        return aeroportoDestino;
    }

    public void setAeroportoDestino(Aeroporto aeroportoDestino) {
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

    public VooEstado getEstado() {
        return estado;
    }

    public void setEstado(VooEstado estado) {
        this.estado = estado;
    }
}
