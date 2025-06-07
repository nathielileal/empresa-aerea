package mscliente.mscliente.model;

import java.time.ZonedDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @ManyToOne
    @JoinColumn(name = "codigo_cliente")
    private Cliente cliente;

    private String codigo_reserva;

    private ZonedDateTime data;

    private Float quantidade_milhas;

    private Double valor;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;

    public Transacao() {
    }

    public Transacao(Cliente cliente, String codigo_reserva, ZonedDateTime data,
            Float milhas, Double valor, String descricao, TipoTransacao tipo) {
        this.cliente = cliente;
        this.codigo_reserva = codigo_reserva;
        this.data = data;
        this.quantidade_milhas = milhas;
        this.valor = valor;
        this.descricao = descricao;
        this.tipo = tipo;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }


    public String getCodigo_reserva() {
        return codigo_reserva;
    }

    public void setCodigo_reserva(String codigo_reserva) {
        this.codigo_reserva = codigo_reserva;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }


    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public Float getQuantidade_milhas() {
        return quantidade_milhas;
    }

    public void setQuantidade_milhas(Float quantidade_milhas) {
        this.quantidade_milhas = quantidade_milhas;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}
