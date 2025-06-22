package com.dac.msreserva.model;

public enum EstadoReservaEnum {
    CRIADA(1L),
    CHECK_IN(2L),
    CANCELADA(3L),
    CANCELADA_VOO(4L),
    EMBARCADA(5L),
    REALIZADO(6L),
    NAO_REALIZADA(7L);

    private final Long codigo;

    EstadoReservaEnum(Long codigo) {
        this.codigo = codigo;
    }

    public Long getCodigo() {
        return codigo;
    }

    @Override
    public String toString() {
        return this.name().replace("_", "-");
    }
}
