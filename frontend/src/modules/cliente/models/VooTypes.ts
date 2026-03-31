import { Voo } from "../../funcionario/models/VooTypes";

export interface Reserva {
    id: string;
    codigo: string;
    voo: Voo;
    quantidade: number;
    milhasUsadas: number;
    valorPago: number;
    estado: 'REALIZADO' | 'CONFIRMADO' | 'CANCELADO';
}

export interface DadosReserva {
    vooId: string;
    clienteId?: string;
    quantidade: number;
    milhasUsadas: number;
}