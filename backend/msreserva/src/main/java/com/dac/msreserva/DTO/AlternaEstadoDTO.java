// package com.dac.msreserva.DTO;

// public class AlternaEstadoDTO {

//     @NotBlank
//     private String estado;

// }
package com.dac.msreserva.DTO;

import java.time.ZonedDateTime;

public class AlternaEstadoDTO {

    private ZonedDateTime data;
    private String codigo_reserva;
    private String estado_reserva;
    private Long codigo_cliente;
    private Double quantidade_milhas;
    private String descricao;
    private Double valor;

    public ZonedDateTime getData() {
        return data;
    }

    public AlternaEstadoDTO(ZonedDateTime data, String codigo_reserva, String estado_reserva,
            Long codigo_cliente, Double quantidade_milhas, String descricao, Double valor) {
        this.data = data;
        this.codigo_reserva = codigo_reserva;
        this.estado_reserva = estado_reserva;
        this.codigo_cliente = codigo_cliente;
        this.quantidade_milhas = quantidade_milhas;
        this.descricao = descricao;
        this.valor = valor;
    }

    /**
     * @param data
     */
    

    public String getCodigo_reserva() {
        return codigo_reserva;
    }

    public void setCodigo_reserva(String codigo_reserva) {
        this.codigo_reserva = codigo_reserva;
    }

    public String getEstado_reserva() {
        return estado_reserva;
    }

    public void setEstado_reserva(String estado_reserva) {
        this.estado_reserva = estado_reserva;
    }

    public Long getCodigo_cliente() {
        return codigo_cliente;
    }

  

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

        public void setData(ZonedDateTime data) {
        this.data = data;
    }

  

    
    // Getters e Setters
}

