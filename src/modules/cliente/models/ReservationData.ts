interface ReservationData {
    codigo: string;
    dataHora: string;
    valor: number;
    milhas: number;
    origem: string;
    destino: string;
    status: 'CONFIRMADO' | 'CANCELADO' | 'PENDENTE';
}

export default ReservationData