package mssaga.mssaga.DTO;

public enum TipoTransacao {
    ENTRADA("ENTRADA"),
    SAIDA("SAÍDA");

    private final String descricao;

    TipoTransacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
