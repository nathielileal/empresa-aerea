import { Aeroporto } from "./AeroportoTypes";

export type Voo = {
    codigo?: string;
    data: string;
    aeroporto_origem: Aeroporto;
    aeroporto_destino: Aeroporto;
    valor_passagem: number;
    quantidade_poltronas_total: number;
    quantidade_poltronas_ocupadas: number;
    estado: string;
  };
