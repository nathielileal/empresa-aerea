package com.dac.msreserva.model;

public enum EstadoReservaEnum {
    CRIADA(1L, "CRIADA", "CRIADA"),
    CHECK_IN(2L, "CHECK-IN", "CHECK_IN"),
    CANCELADA(3L, "CANCELADA", "CANCELADA"),
    CANCELADA_VOO(4L, "CANCELADA VOO", "CANCELADA_VOO"),
    EMBARCADA(5L, "EMBARCADA", "EMBARCADA"),
    REALIZADA(6L, "REALIZADA", "REALIZADA"),
    NAO_REALIZADA(7L, "NÃO REALIZADA", "NAO_REALIZADA");

    private final Long codigo;
    private final String nome;
    private final String campo;

    EstadoReservaEnum(Long codigo, String nome, String campo) {
        this.codigo = codigo;
        this.nome = nome;
        this.campo = campo;
    }

    public Long getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getCampo() {
        return campo;
    }
}
