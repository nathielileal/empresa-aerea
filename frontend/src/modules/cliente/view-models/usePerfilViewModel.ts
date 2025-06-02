// modules/cliente/view-models/usePerfilViewModel.ts
import { useState, useEffect } from 'react';
import { useAuth } from '../../../shared/contexts/AuthContext';
import { reservaService } from '../services/reservaService';
import { Reserva } from '../models/ReservaTypes';
import { Cliente } from '../models/ClienteTypes';
import { useMilhas } from './useClienteMilhasViewModel';

export function usePerfilViewModel() {
  const { user } = useAuth();
  const [reservas, setReservas] = useState<Reserva[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [saldoMilhas, setSaldoMilhas] = useState(Number)

  const cliente = user?.tipo === 'CLIENTE' ? user as Cliente : null;

  const { transactions, getSaldoMilhas } = useMilhas(); 

  const carregarDadosReservas = async () => {
    if (!user?.id) return;

    setLoading(true);
    try {
      const reservasData = await reservaService.getReservas(user.id, {
      });
      setReservas(reservasData);
    } catch (err) {
      setError('Erro ao carregar reservas');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    // const milhasCompradas = getSaldoMilhas();
    if(cliente?.saldoMilhas)
    // const milhasOriginais = cliente?.saldoMilhas ?? 0;
    setSaldoMilhas(cliente?.saldoMilhas);
  }, [cliente?.saldoMilhas, transactions]);

  const cancelarReserva = async (reservaId: string) => {
    try {
      const reserva = reservas.find(r => r.id === reservaId);

      if (!reserva || !['CRIADA', 'CHECK-IN'].includes(reserva.estado)) {
        setError('Reserva não pode ser cancelada');
        return;
      }

      await reservaService.cancelarReserva(reservaId);

      const milhasRestituir = reserva.milhasGastas ?? 0;
      if (milhasRestituir > 0 && user?.id) {
        // await clienteService.restituirMilhas(...)
      }

      // Atualiza saldo no frontend
      setSaldoMilhas(prev => prev + milhasRestituir);
    } catch (err) {
      setError('Erro ao cancelar reserva');
    }
  };

  const carregarDados = async () => {
    await carregarDadosReservas();
  };

  useEffect(() => {
    carregarDados();
  }, [user?.id]);

  return {
    user,
    reservas,
    loading,
    error,
    saldoMilhas,
    cancelarReserva,
    recarregar: carregarDados
  };
}