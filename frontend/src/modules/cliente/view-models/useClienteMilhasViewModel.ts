import { useState, useEffect } from "react";
import { Transaction } from "../models/TransactionTypes";
import axios from "axios";
import { useAuth } from "../../../shared/contexts/AuthContext";
import { MilhasService } from "../services/milhasService";



export const useMilhas = () => {
  const [transactions, setTransactions] = useState<Transaction[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const { user } = useAuth();
  const clienteId = Number(user?.id)
  const fetchTransactions = async () => {
    if (!clienteId) return;
    try {
      setLoading(true);
      const data = await MilhasService.getTransactions(clienteId);
      setTransactions(data);
    } catch (err) {
      console.error("Erro ao buscar transações:", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchTransactions();
  }, [clienteId]);

  const getSaldoMilhas = () => {
    return transactions.reduce((total, t) => {
      return t.tipo === "ENTRADA" ? total + t.milhas : total - t.milhas;
    }, 0);
  };

  const buyMiles = async (valor: number) => {
    const milhas = valor / 5;
    if (!clienteId) return;

    try {
      await MilhasService.buyMiles(clienteId, milhas);
      await fetchTransactions();
    } catch (err) {
      console.error("Erro ao comprar milhas:", err);
      throw err;
    }
  };

  return { transactions, buyMiles, getSaldoMilhas, loading };
};
