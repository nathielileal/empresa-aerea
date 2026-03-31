package mssaga.mssaga.DTO;

public class ReservaInputDTO {
    private double valor;
    private double milhas_utilizadas;
    private int quantidade_poltronas;
    private long codigo_cliente;
    private String codigo_voo;

    public ReservaInputDTO() {
    }

    public ReservaInputDTO(double valor, double milhas_utilizadas, int quantidade_poltronas, long codigo_cliente,
            String codigo_voo) {
        this.valor = valor;
        this.milhas_utilizadas = milhas_utilizadas;
        this.quantidade_poltronas = quantidade_poltronas;
        this.codigo_cliente = codigo_cliente;
        this.codigo_voo = codigo_voo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getMilhas_utilizadas() {
        return milhas_utilizadas;
    }

    public void setMilhas_utilizadas(double milhas_utilizadas) {
        this.milhas_utilizadas = milhas_utilizadas;
    }

    public int getQuantidade_poltronas() {
        return quantidade_poltronas;
    }

    public void setQuantidade_poltronas(int quantidade_poltronas) {
        this.quantidade_poltronas = quantidade_poltronas;
    }

    public long getCodigo_cliente() {
        return codigo_cliente;
    }

    public void setCodigo_cliente(long codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }

    public String getCodigo_voo() {
        return codigo_voo;
    }

    public void setCodigo_voo(String codigo_voo) {
        this.codigo_voo = codigo_voo;
    }

}
