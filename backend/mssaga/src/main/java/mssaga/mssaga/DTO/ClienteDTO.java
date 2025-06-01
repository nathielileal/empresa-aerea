package mssaga.mssaga.DTO;

public class ClienteDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private float saldoMilhas;
    private EnderecoDTO endereco;

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

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }

}
