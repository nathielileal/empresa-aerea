export interface Transacao{
    codigo: string;
    codigo_reserva: string | null;
    data: Date;
    quantidade_milhas: number;
    valor: number;
    descricao: string;
    tipo: TipoTransacao;
  }

  export enum TipoTransacao {
    ENTRADA = "ENTRADA",
    SAIDA = "SAIDA"
  }