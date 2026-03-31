import { Voo } from "../../funcionario/models/VooTypes";

export type EstadoReserva = 'CHECK-IN'|'NÃO REALIZADA'| 'CANCELADA VOO'| 'EMBARCADA'| 'CRIADA' | 'REALIZADA' | 'CANCELADA';

export interface Reserva {
  codigo: string;
  codigo_cliente: number,
  voo: Voo; // ISO string
  estado: string;
  data: string;
  quantidade_milhas: number;
  valor: number;
}

export interface FiltroReservas {
  estados?: EstadoReserva[];
  ordenarPor?: 'dataHora' | 'valor';
}