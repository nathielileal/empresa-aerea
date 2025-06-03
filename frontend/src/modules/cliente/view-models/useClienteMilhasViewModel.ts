import { useState, useEffect } from "react";
import axios from "axios";
import { useAuth } from "../../../shared/contexts/AuthContext";
import { MilhasService } from "../services/milhasService";
import { Extrato } from "../models/ExtratoTypes";
import { Cliente } from "../models/ClienteTypes";



export const useMilhas = () => {
  const [extrato, setExtrato] = useState<Extrato>();
  const [loading, setLoading] = useState<boolean>(false);
  const { user } = useAuth();
  const cliente = user?.tipo === 'CLIENTE' ? user as Cliente : null;
  const clienteId = cliente?.codigo
  const fetchTransactions = async () => {
    if (!clienteId) return;
    try {
      setLoading(true);
      const data = await MilhasService.getExtrato(clienteId);
      setExtrato(data);
    } catch (err) {
      console.error("Erro ao buscar transações:", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    console.log("Buscando extrato para o cliente", clienteId)
    console.log("Usuario", user)
    console.log("Cliente", cliente)
    fetchTransactions();
  }, [clienteId]);

  const getSaldo_milhas = () => {
    return extrato?.saldo_milhas;
  };

  const buyMiles = async (valor: number) => {
    console.log("comprando", valor / 5)
    const milhas = valor / 5;
    console.log("ID do cliente", clienteId)
    if (!clienteId) return;

    try {
      await MilhasService.buyMiles(clienteId, milhas);
      await fetchTransactions();
    } catch (err) {
      console.error("Erro ao comprar milhas:", err);
      throw err;
    }
  };

  return { extrato, buyMiles, getSaldo_milhas, loading, fetchTransactions };
};
