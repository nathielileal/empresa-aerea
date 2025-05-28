package mscliente.mscliente.model;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_clientes")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long id;

    @Column(name = "nome_cliente")
    private String nome;

    @Column(name = "cpf_cliente")
    private String cpf;

    @Column(name = "email_cliente")
    private String email;

    @Column(name = "saldoMilhas_cliente")
    private float saldoMilhas;

    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    private boolean ativo;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getSaldoMilhas() {
        return saldoMilhas;
    }

    public void setSaldoMilhas(float saldoMilhas) {
        this.saldoMilhas = saldoMilhas;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

}
