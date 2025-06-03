import { mockDatabase, MockUser } from "../../auth/mocks/mockDatabase";
import { Cliente } from "../models/ClienteTypes";

export const clienteService = {
  async getSaldo_milhas(clienteId: string): Promise<number> {
    console.log(clienteId)
    return 3000;
  },

  async restituirMilhas() { },

  async debitarMilhas() { },

  async cadastrar(cliente: Cliente) {
    console.log("enviando os dados para cadastro do cliente:", cliente)
    try {
      const response = await fetch('http://localhost:3000/clientes', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(cliente),
      });

      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`Erro ao cadastrar cliente: ${errorText}`);
      }

      return await response.json();

    } catch (error) {
      console.log(error)
    }

  }

};
;
