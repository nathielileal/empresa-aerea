import { Cliente } from "../models/ClienteTypes";

export const clienteService = {
  async getSaldo_milhas(clienteId: string): Promise<number> {
    console.log(clienteId)
    return 3000;
  },

  async getById(clienteId: string): Promise<Cliente> {
    try {
      console.log("Buscando", clienteId);

      const token = localStorage.getItem('access_token'); // pega o token

      const response = await fetch(`http://localhost:3000/clientes/${clienteId}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`, // inclui o token
        },
      });

      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`Erro ao buscar cliente: ${errorText}`);
      }

      const cliente: Cliente = await response.json();
      return cliente;

    } catch (error) {
      console.log(error);
      throw error;
    }
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
