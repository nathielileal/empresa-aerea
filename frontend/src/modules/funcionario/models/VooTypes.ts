import { Aeroporto } from "./AeroportoTypes";

export type Voo = {
    codigo: string;
    data: string;
    aeroporto_origem: Aeroporto;
    aeroporto_destino: Aeroporto;
    valorPassagem: number;
    // valorMilhas: number;
    // poltronas: number;
    // poltronasOcupadas: number;
    estado: string;
    valor_passagem: number;
    // milhasNecessarias: number;
    // assentosDisponiveis?: number;
  };
  