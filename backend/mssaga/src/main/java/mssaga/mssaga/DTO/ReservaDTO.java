package mssaga.mssaga.DTO;

import java.time.ZonedDateTime;
import java.util.List;

public class ReservaDTO {

    private String codigo;
    private ZonedDateTime data;
    private String estado;
    private Double quantidade_milhas;
    private Long codigo_cliente;
    private Double saldo_cliente;
    private List<Integer> poltronas_reservadas;
    private VooDTO voo;
    private String voo_codigo;

    public String getCodigo() {
        return codigo;
    }

    public ReservaDTO(String codigo, ZonedDateTime data, String estado, Double quantidade_milhas, Long codigo_cliente,
            Double saldo_cliente, List<Integer> poltronas_reservadas, VooDTO voo, String voo_codigo) {
        this.codigo = codigo;
        this.data = data;
        this.estado = estado;
        this.quantidade_milhas = quantidade_milhas;
        this.codigo_cliente = codigo_cliente;
        this.saldo_cliente = saldo_cliente;
        this.poltronas_reservadas = poltronas_reservadas;
        this.voo = voo;
        this.voo_codigo = voo_codigo;
    }

    public ReservaDTO() {
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

    public Long getCodigo_cliente() {
        return codigo_cliente;
    }

    public void setCodigo_cliente(Long codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }

    public Double getSaldo_cliente() {
        return saldo_cliente;
    }

    public void setSaldo_cliente(Double saldo_cliente) {
        this.saldo_cliente = saldo_cliente;
    }

    public List<Integer> getPoltronas_reservadas() {
        return poltronas_reservadas;
    }

    public void setPoltronas_reservadas(List<Integer> poltronas_reservadas) {
        this.poltronas_reservadas = poltronas_reservadas;
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

    // Getters e Setters
}
