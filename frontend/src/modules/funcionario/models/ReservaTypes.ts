export type EstadoReserva =
    | "CRIADA"
    | "CHECK-IN"
    | "EMBARCADA"
    | "CANCELADA"
    | "REALIZADA"
    | "NÃO REALIZADA"
    | "CANCELADO VOO";

export interface Reserva {
  codigo: string;
  codigo_cliente: number;
  codigo_voo: string;
  estado: EstadoReserva;
  quantidade_milhas: number;
  quantidade_poltronas: number;
}
