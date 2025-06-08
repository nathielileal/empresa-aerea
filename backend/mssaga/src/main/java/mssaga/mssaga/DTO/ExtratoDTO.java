package mssaga.mssaga.DTO;

import java.util.List;

public class ExtratoDTO {
    private Long codigo;
    private float saldo_milhas;
    private List<TransacaoDTO> transacoes;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public float getSaldo_milhas() {
        return saldo_milhas;
    }

    public void setSaldo_milhas(float saldo_milhas) {
        this.saldo_milhas = saldo_milhas;
    }

    public List<TransacaoDTO> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(List<TransacaoDTO> transacoes) {
        this.transacoes = transacoes;
    }
}
