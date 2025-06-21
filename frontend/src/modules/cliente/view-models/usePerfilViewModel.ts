// modules/cliente/view-models/usePerfilViewModel.ts
import { useState, useEffect } from 'react';
import { useAuth } from '../../../shared/contexts/AuthContext';
import { reservaService } from '../services/reservaService';
import { Reserva } from '../models/ReservaTypes';
import { Cliente } from '../models/ClienteTypes';
import { useMilhas } from './useClienteMilhasViewModel';
import { clienteService } from '../services/clienteService';

export function usePerfilViewModel() {
  const { user, refreshUser } = useAuth();
  const [reservas, setReservas] = useState<Reserva[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [saldo_milhas, setSaldo_milhas] = useState(Number)

  const cliente = user?.tipo === 'CLIENTE' ? user as Cliente : null;
  console.log("Cliente na tela inicial", cliente)
  const { fetchTransactions } = useMilhas();

  const carregarDadosReservas = async () => {
    if (!user?.codigo) return;

    setLoading(true);
    try {
      const reservasData = await reservaService.getReservas(user.codigo, {
      });
      setReservas(reservasData);
    } catch (err) {
      setError('Erro ao carregar reservas');
    } finally {
      setLoading(false);
    }
  };

  const getCliente = async () => {
    if (!user?.codigo) return;

    setLoading(true);
    try {
      const cliente = await clienteService.getById(user.codigo);
      setSaldo_milhas(cliente.saldo_milhas);
    } catch (err) {
      setError('Erro ao carregar dados do cliente');
    } finally {
      setLoading(false);
    }
  };


  useEffect(() => {
    // const milhasCompradas = getsaldo_milhas();
    if (cliente?.saldo_milhas)
      // const milhasOriginais = cliente?.saldo_milhas ?? 0;
      setSaldo_milhas(cliente?.saldo_milhas);
  }, [cliente?.saldo_milhas]);

  const cancelarReserva = async (reservaId: string) => {
    try {
      const reserva = reservas.find(r => r.codigo === reservaId);

      if (!reserva || !['CRIADA', 'CHECK-IN'].includes(reserva.estado)) {
        setError('Reserva não pode ser cancelada');
        return;
      }

      await reservaService.cancelarReserva(reservaId);

    } catch (err) {
      setError('Erro ao cancelar reserva');
    }
  };

  const carregarDados = async () => {
    await carregarDadosReservas();
    await getCliente()
  };

  useEffect(() => {
    carregarDados();
  }, [user?.codigo]);

  return {
    user,
    reservas,
    loading,
    error,
    saldo_milhas,
    cancelarReserva,
    recarregar: carregarDados
  };
}