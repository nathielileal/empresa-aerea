import { useState } from 'react';
import ReservationData from '../models/ReservationData';



export const useReservationConsultaViewModel = () => {
    const [codigoBusca, setCodigoBusca] = useState('');
    const [reserva, setReserva] = useState<ReservationData | null>(null);
    const [carregando, setCarregando] = useState(false);

    const buscarReserva = async () => {
        if (!codigoBusca.trim()) return;
        setCarregando(true);

        try {
            const resposta = await new Promise<ReservationData>((resolve) =>
                setTimeout(() => {
                    resolve({
                        codigo: codigoBusca,
                        dataHora: '15/10/2023 14:30',
                        valor: 450.9,
                        milhas: 2000,
                        origem: 'São Paulo (GRU)',
                        destino: 'Rio de Janeiro (GIG)',
                        status: 'CONFIRMADO',
                    });
                }, 1000)
            );

            setReserva(resposta);
        } catch (erro) {
            console.error('Erro ao buscar reserva', erro);
            setReserva(null);
        } finally {
            setCarregando(false);
        }
    };

    const cancelarReserva = () => {
        if (!reserva) return;
        setReserva({ ...reserva, status: 'CANCELADO' });
    };

    return {
        codigoBusca,
        setCodigoBusca,
        reserva,
        buscarReserva,
        cancelarReserva,
        carregando,
    };
};
