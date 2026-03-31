import { useState, useEffect } from "react";
import { Voo } from "../models/VooTypes";
import { Reserva } from "../models/ReservaTypes";
import { vooService } from "../services/vooService";
import { reservaService } from "../services/reservaService";

export const VooViewModel = () => {
  const [flights, setFlights] = useState<Voo[]>([]);
  const [reservas] = useState<Reserva[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const loadFlights = async () => {
    setLoading(true);
    setError("");

    try {
      const now = new Date();
      const inicio = now.toISOString().slice(0, 10);
      const fim = new Date(now.getTime() + 48 * 60 * 60 * 1000).toISOString().slice(0, 10);

      const voosProximos48h = await vooService.listarVoosPorIntervalo(inicio, fim);

      setFlights(voosProximos48h.sort((a, b) => new Date(a.data).getTime() - new Date(b.data).getTime()));
    } catch (e) {
      setError("Erro ao carregar voos");
    } finally {
      setLoading(false);
    }
  };

  const confirmarEmbarque = async (codigoReserva: string) => {
    try {
      await reservaService.atualizarEstadoReserva(codigoReserva, "EMBARCADA");
      
      await loadFlights();
    } catch (e: any) {
      setError(e.message || "Erro ao confirmar embarque");
    }
  };

  const cancelarVoo = async (codigoVoo: string) => {
    try {
      await vooService.cancelarVoo(codigoVoo);

      await loadFlights();
    } catch {
      setError("Erro ao cancelar voo");
    }
  };

  const realizarVoo = async (codigoVoo: string) => {
    try {
      await vooService.realizarVoo(codigoVoo);

      await loadFlights();
    } catch {
      setError("Erro ao realizar voo");
    }
  };

  useEffect(() => {
    loadFlights();
  }, []);

  return {
    flights,
    reservas,
    loading,
    error,
    loadFlights,
    confirmarEmbarque,
    cancelarVoo,
    realizarVoo,
  };
};
