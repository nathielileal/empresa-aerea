package mssaga.mssaga.DTO;
import java.util.List;

public class ReservaTransactionDTO {

    private float valor;
    private float milhas_utilizadas;
    private Integer quantidade_poltronas;
    private Long codigo_cliente;
    private VooDTO voo;

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public float getMilhas_utilizadas() {
        return milhas_utilizadas;
    }

    public void setMilhas_utilizadas(float milhas_utilizadas) {
        this.milhas_utilizadas = milhas_utilizadas;
    }

    public Integer getQuantidade_poltronas() {
        return quantidade_poltronas;
    }

    public void setQuantidade_poltronas(Integer quantidade_poltronas) {
        this.quantidade_poltronas = quantidade_poltronas;
    }

    public Long getCodigo_cliente() {
        return codigo_cliente;
    }

    public void setCodigo_cliente(Long codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }

    public VooDTO getVoo() {
        return voo;
    }

    public void setVoo(VooDTO voo) {
        this.voo = voo;
    }

    public ReservaTransactionDTO(float valor, float milhas_utilizadas, Integer quantidade_poltronas,
            Long codigo_cliente, VooDTO voo) {
        this.valor = valor;
        this.milhas_utilizadas = milhas_utilizadas;
        this.quantidade_poltronas = quantidade_poltronas;
        this.codigo_cliente = codigo_cliente;
        this.voo = voo;
    }

    public ReservaTransactionDTO(double valor2, double milhas_utilizadas2, int quantidade_poltronas2,
            long codigo_cliente2, VooDTO dadosVoo) {
        //TODO Auto-generated constructor stub
    }


}

