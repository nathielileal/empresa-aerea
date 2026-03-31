import { EstadoReserva } from "../models/ReservaTypes";

const API_URL = "http://localhost:3000"; 

function getToken(): string | null {
  return localStorage.getItem("token");
}

export class ReservaService {
  async atualizarEstadoReserva(codigo: string, novoEstado: EstadoReserva): Promise<void> {
    const token = getToken();

    const res = await fetch(`${API_URL}/reservas/${codigo}/estado`, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({ estado: novoEstado }),
    });

    if (!res.ok) {
      const msg = await res.text();
      throw new Error("Erro ao alterar estado da reserva: " + msg);
    }
  }
}

export const reservaService = new ReservaService();
