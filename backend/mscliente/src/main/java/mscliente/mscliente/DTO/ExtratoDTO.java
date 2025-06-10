package mscliente.mscliente.DTO;

import java.util.List;

public class ExtratoDTO {
    private Long codigo;
    private double saldo_milhas;
    private List<TransacaoDTO> transacoes;

    public Long getCodigo() {
        return codigo;
    }

    public ExtratoDTO() {
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public ExtratoDTO(Long codigo, double saldo_milhas, List<TransacaoDTO> transacoes) {
        this.codigo = codigo;
        this.saldo_milhas = saldo_milhas;
        this.transacoes = transacoes;
    }

    public double getSaldo_milhas() {
        return saldo_milhas;
    }

    public void setSaldo_milhas(double saldo_milhas) {
        this.saldo_milhas = saldo_milhas;
    }

    public List<TransacaoDTO> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(List<TransacaoDTO> transacoes) {
        this.transacoes = transacoes;
    }
}
