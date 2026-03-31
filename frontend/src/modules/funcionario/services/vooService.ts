import { Aeroporto } from "../models/AeroportoTypes";
import { Voo } from "../models/VooTypes";

const API_URL = "http://localhost:3000/voos";

function getToken(): string | null {
  return localStorage.getItem("token");
}

export class VooService {
  async listar(): Promise<Voo[]> {
    try {
      const token = getToken();

      const response = await fetch(API_URL, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        throw new Error("Erro ao buscar voos");
      }

      return await response.json();
    } catch (error) {
      console.log(error);
      throw error;
    }
  }

  async listarAeroportos(): Promise<Aeroporto[]> {
    try {
      const token = getToken();

      const response = await fetch(API_URL + "/aeroportos", {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        throw new Error("Erro ao buscar aeroportos");
      }

      return await response.json();
    } catch (error) {
      console.log(error);
      throw error;
    }
  }

  async listarVoosPorIntervalo(inicio: string, fim: string): Promise<Voo[]> {
    try {
      const token = getToken();

      const response = await fetch(API_URL + `?inicio=${inicio}&fim=${fim}`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        const erro = await response.text();
        throw new Error("Erro ao cadastrar voo: " + erro);
      }

      const data = await response.json();
      
      return data.voos;
    } catch (error) {
      console.log(error);
      throw error;
    }
  }

  async cancelarVoo(codigo: string): Promise<void> {
    try {
      const token = getToken();

      await fetch(API_URL + `/${codigo}/cancelar`, {
        method: "PUT",
        headers: {
          // "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });
    } catch (error) {
      console.log(error);
      throw error;
    }
  }

  async realizarVoo(codigo: string): Promise<void> {
    try {
      const token = getToken();

      await fetch(API_URL + `/${codigo}/realizar`, {
        method: "PUT",
        headers: {
          // "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });
    } catch (error) {
      console.log(error);
      throw error;
    }
  }

  async adicionar(voo: {
    data: string;
    valor_passagem: number;
    quantidade_poltronas_total: number;
    quantidade_poltronas_ocupadas: number;
    codigo_aeroporto_origem: string;
    codigo_aeroporto_destino: string;
  }): Promise<{ codigo: string }> {
    try {
      const token = getToken();

      const response = await fetch(API_URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(voo),
      });

      if (!response.ok) {
        const erro = await response.text();
        throw new Error("Erro ao cadastrar voo: " + erro);
      }

      return await response.json();
    } catch (error) {
      console.log(error);
      throw error;
    }
  }
}

export const vooService = new VooService();
