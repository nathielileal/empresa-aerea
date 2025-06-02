import axios from "axios";
import { Transaction } from "../models/TransactionTypes";

const API_URL = "http://localhost:3000/clientes";

export const MilhasService = {
    async getTransactions(clienteId: number): Promise<Transaction[]> {
        const res = await axios.get(`${API_URL}/${clienteId}/transacoes`);
        return res.data.map((t: any) => ({
            ...t,
            data: new Date(t.data),
        }));
    },

    async buyMiles(clienteId: number, milhas: number): Promise<void> {
        await axios.put(`${API_URL}/${clienteId}/milhas`, { milhas });
    },
};
