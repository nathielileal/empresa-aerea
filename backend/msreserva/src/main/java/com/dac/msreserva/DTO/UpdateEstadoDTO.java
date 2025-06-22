package com.dac.msreserva.DTO;

import java.time.ZonedDateTime;

public class UpdateEstadoDTO {
    private String codigo;
    private String estado;
    private ZonedDateTime data;
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getEstado() {
        return estado;
    }
    public UpdateEstadoDTO() {
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public UpdateEstadoDTO(String codigo, String estado, ZonedDateTime data) {
        this.codigo = codigo;
        this.estado = estado;
        this.data = data;
    }
    public ZonedDateTime getData() {
        return data;
    }
    public void setData(ZonedDateTime data) {
        this.data = data;
    }
}
