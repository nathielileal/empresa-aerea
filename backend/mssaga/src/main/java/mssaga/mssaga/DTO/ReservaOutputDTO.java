package mssaga.mssaga.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.ZonedDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservaOutputDTO {
    private String codigo;
    private ZonedDateTime data;
    private String estado;
    private Double quantidade_milhas;
    private long codigo_cliente;
    private Double saldo_cliente;
    // private List<Integer> poltronasReservadas;
    private VooDTO voo;
    private String voo_codigo;

    public ReservaOutputDTO() {
    }

    public ReservaOutputDTO(String codigo, ZonedDateTime data, String estado, Double quantidade_milhas,
            long codigo_cliente, Double saldo_cliente, VooDTO voo, String voo_codigo) {
        this.codigo = codigo;
        this.data = data;
        this.estado = estado;
        this.quantidade_milhas = quantidade_milhas;
        this.codigo_cliente = codigo_cliente;
        this.saldo_cliente = saldo_cliente;
        this.voo = voo;
        this.voo_codigo = voo_codigo;
    }



    public ReservaOutputDTO(String codigo, ZonedDateTime data, String estado, Double quantidade_milhas,
            long codigo_cliente, Double saldo_cliente) {
        this.codigo = codigo;
        this.data = data;
        this.estado = estado;
        this.quantidade_milhas = quantidade_milhas;
        this.codigo_cliente = codigo_cliente;
        this.saldo_cliente = saldo_cliente;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getQuantidade_milhas() {
        return quantidade_milhas;
    }

    public void setQuantidade_milhas(Double quantidade_milhas) {
        this.quantidade_milhas = quantidade_milhas;
    }

    public long getCodigo_cliente() {
        return codigo_cliente;
    }

    public void setCodigo_cliente(long codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }

    public Double getSaldo_cliente() {
        return saldo_cliente;
    }

    public void setSaldo_cliente(Double saldo_cliente) {
        this.saldo_cliente = saldo_cliente;
    }

    public VooDTO getVoo() {
        return voo;
    }

    public void setVoo(VooDTO voo) {
        this.voo = voo;
    }

    public String getVoo_codigo() {
        return voo_codigo;
    }

    public void setVoo_codigo(String voo_codigo) {
        this.voo_codigo = voo_codigo;
    }
}
