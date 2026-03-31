package com.dac.msreserva.DTO;

import java.time.ZonedDateTime;

import com.dac.msreserva.model.ReservaConsulta;

public class ReservaConsultaDTO {

    private String codigo;
    private Long codigo_cliente;
    private VooDTO voo;
    private String estado;
    private Double quantidade_milhas;
    private ZonedDateTime data;
    private Double valor;

    public ReservaConsultaDTO(ReservaConsulta reserva) {
        this.codigo = reserva.getCodigo();
        this.codigo_cliente = reserva.getCodigo_cliente();
        this.estado = convertTextCampoEstadoReserva(reserva.getEstado());
        this.data = reserva.getData();
        this.quantidade_milhas = reserva.getQuantidade_milhas();

        this.voo = new VooDTO(
                reserva.getCodigo_voo(),
                reserva.getDataVoo(),
                new AeroportoDTO(reserva.getAeroporto_origem()),
                new AeroportoDTO(reserva.getAeroporto_destino()));
        this.valor = reserva.getValor();
    }

    public ReservaConsultaDTO(String codigo, Long codigo_cliente, VooDTO voo, String estado, Double quantidade_milhas,
            ZonedDateTime data, Double valor) {
        this.codigo = codigo;
        this.codigo_cliente = codigo_cliente;
        this.voo = voo;
        this.estado = estado;
        this.quantidade_milhas = quantidade_milhas;
        this.data = data;
        this.valor = valor;
    }

    public Double getQuantidade_milhas() {
        return quantidade_milhas;
    }

    public void setQuantidade_milhas(Double quantidade_milhas) {
        this.quantidade_milhas = quantidade_milhas;
    }

    public ReservaConsultaDTO(String codigo, Long codigo_cliente, VooDTO voo, String estado, ZonedDateTime data,
            Double quantidade_milhas) {
        this.codigo = codigo;
        this.codigo_cliente = codigo_cliente;
        this.voo = voo;
        this.estado = estado;
        this.data = data;   
    }

    public ReservaConsultaDTO() {
    }

    public Long getCodigo_cliente() {
        return codigo_cliente;
    }

    public void setCodigo_cliente(Long codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public VooDTO getVoo() {
        return voo;
    }

    public void setVoo(VooDTO voo) {
        this.voo = voo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    private String convertTextCampoEstadoReserva(String estado) {
        if (estado == null) return null;

        switch (estado) {
            case "CRIADA":
                return "CRIADA";
            
            case "CHECK_IN":
                return "CHECK-IN";
            
            case "CANCELADA":
                return "CANCELADA";
            
            case "CANCELADA_VOO":
            case "CANCELADA-VOO":
                return "CANCELADA VOO";
        
            case "EMBARCADA":
                return "EMBARCADA";
            
            case "REALIZADO":
                return "REALIZADA";  
            
            case "NAO-REALIZADA":
            case "NAO_REALIZADA":
                return "NÃO REALIZADA";
            
            default:
                return estado;  
        }
    }
}
