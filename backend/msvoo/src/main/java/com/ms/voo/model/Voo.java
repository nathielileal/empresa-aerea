package com.ms.voo.model;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.Random;

@Entity
@Table(name = "voo")
public class Voo {

    @Id
    private String codigo;

    private ZonedDateTime data;

    @ManyToOne
    @JoinColumn(name = "aeroporto_origem", referencedColumnName = "codigo")
    private Aeroporto aeroporto_origem;

    @ManyToOne
    @JoinColumn(name = "aeroporto_destino", referencedColumnName = "codigo")
    private Aeroporto aeroporto_destino;

    private double valor_passagem;
    private int quantidade_poltronas_total;
    private int quantidade_poltronas_ocupadas;

    @ManyToOne(optional = false)
    @JoinColumn(name = "estado_codigo", referencedColumnName = "codigo")
    private VooEstado estado;

    public Voo() {
    }

    public Voo(ZonedDateTime data, Aeroporto origem, Aeroporto destino) {
        this.codigo = "TADS" + String.format("%04d", new Random().nextInt(10000));
        this.data = data;
        this.aeroporto_origem = origem;
        this.aeroporto_destino = destino;
        this.quantidade_poltronas_total = 100;
        this.quantidade_poltronas_ocupadas = 0;
        this.valor_passagem = 350.00;
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

    public Aeroporto getAeroportoOrigem() {
        return aeroporto_origem;
    }

    public void setAeroportoOrigem(Aeroporto aeroporto_origem) {
        this.aeroporto_origem = aeroporto_origem;
    }

    public Aeroporto getAeroportoDestino() {
        return aeroporto_destino;
    }

    public void setAeroportoDestino(Aeroporto aeroporto_destino) {
        this.aeroporto_destino = aeroporto_destino;
    }

    public double getValorPassagem() {
        return valor_passagem;
    }

    public void setValorPassagem(double valor_passagem) {
        this.valor_passagem = valor_passagem;
    }

    public int getQuantidadePoltronas() {
        return quantidade_poltronas_total;
    }

    public void setQuantidadePoltronas(int quantidade_poltronas_total) {
        this.quantidade_poltronas_total = quantidade_poltronas_total;
    }

    public int getQuantidadeOcupadas() {
        return quantidade_poltronas_ocupadas;
    }

    public void setQuantidadeOcupadas(int quantidade_poltronas_ocupadas) {
        this.quantidade_poltronas_ocupadas = quantidade_poltronas_ocupadas;
    }

    public VooEstado getEstado() {
        return estado;
    }

    public void setEstado(VooEstado estado) {
        this.estado = estado;
    }
}
