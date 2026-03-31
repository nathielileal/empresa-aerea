import { Transacao } from "./TransacaoTypes";

export interface Extrato {
    codigo: string;
    saldo_milhas: number;
    transacoes: Transacao[];
}