import axios from "axios";
import { Extrato } from "../models/ExtratoTypes";

const API_URL = "http://localhost:3000/clientes";

const getAuthHeader = () => {
  const token = localStorage.getItem("access_token");
  return {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };
};

export const MilhasService = {
  async getExtrato(clienteId: string): Promise<Extrato> {
    const res = await axios.get(`${API_URL}/${clienteId}/milhas`, getAuthHeader());
    return {
      ...res.data,
      transacoes: res.data.transacoes.map((t: any) => ({
        ...t,
        data: new Date(t.data),
      })),
    };
  },

  async buyMiles(clienteId: string, quantidade: number): Promise<void> {
    await axios.put(`${API_URL}/${clienteId}/milhas`, { quantidade }, getAuthHeader());
  },
};
