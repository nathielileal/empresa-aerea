package mscliente.mscliente.DTO;

import java.time.ZonedDateTime;

public class ReservaCreationResponseDTO {

    private ZonedDateTime data;
    private String codigo_reserva;
    private String estado_reserva;
    private Long codigo_cliente;
    private double quantidade_milhas;
    private String descricao;
    private double valor;

    public ZonedDateTime getData() {
        return data;
    }

    public ReservaCreationResponseDTO(ZonedDateTime data, String codigo_reserva, String estado_reserva,
            Long codigo_cliente, double quantidade_milhas, String descricao, double valor) {
        this.data = data;
        this.codigo_reserva = codigo_reserva;
        this.estado_reserva = estado_reserva;
        this.codigo_cliente = codigo_cliente;
        this.quantidade_milhas = quantidade_milhas;
        this.descricao = descricao;
        this.valor = valor;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

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

    public void setCodigo_cliente(Long codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }

    public double getQuantidade_milhas() {
        return quantidade_milhas;
    }

    public void setQuantidade_milhas(double quantidade_milhas) {
        this.quantidade_milhas = quantidade_milhas;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    // Getters e Setters
}
