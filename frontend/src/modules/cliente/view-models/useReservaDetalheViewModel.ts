import { useState, useEffect } from 'react';
import { reservaService } from '../services/reservaService';
import { Reserva } from '../models/ReservaTypes';

export function useReservaDetalheViewModel(reservaId: string) {
  const [reserva, setReserva] = useState<Reserva | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const carregarReserva = async () => {
    setLoading(true);
    try {
      const data = await reservaService.getReservaDetalhes(reservaId);
      setReserva(data);
    } catch (err) {
      setError('Reserva não encontrada');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    carregarReserva();
  }, [reservaId]);

  return {
    reserva,
    loading,
    error
  };
}