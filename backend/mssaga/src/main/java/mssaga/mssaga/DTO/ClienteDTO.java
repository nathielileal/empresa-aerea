package mssaga.mssaga.DTO;

public class ClienteDTO {
    private Long codigo;
    private String nome;
    private String cpf;
    private String email;
    private float saldo_milhas;
    private EnderecoDTO endereco;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
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


    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }

    public float getSaldo_milhas() {
        return saldo_milhas;
    }

    public void setSaldo_milhas(float saldo_milhas) {
        this.saldo_milhas = saldo_milhas;
    }

}
