import { Funcionario } from '../models/FuncionarioTypes';

const API_URL = 'http://localhost:3000/funcionarios';

function getToken(): string | null {
  return localStorage.getItem('token');
}

export class FuncionarioService {
  async listar(): Promise<Funcionario[]> {
    try {
      const token = getToken();

      const response = await fetch(API_URL, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        throw new Error('Erro ao buscar funcionários');
      }

      return await response.json();
    } catch (error) {
      console.error(error);
      throw error;
    }
  }

  async criar(funcionario: Omit<Funcionario, 'codigo' | 'ativo'>): Promise<Funcionario> {
    try {
      const token = getToken();

      const response = await fetch(API_URL, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(funcionario),
      });

      if (response.status !== 201) {
        const erro = await response.text();
        throw new Error('Erro ao criar funcionário: ' + erro);
      }

      return await response.json();
    } catch (error) {
      console.error(error);
      throw error;
    }
  }

  async atualizar(
    codigo: number,
    dados: Partial<Omit<Funcionario, 'codigo' | 'cpf' | 'ativo'>>
  ): Promise<Funcionario> {
    try {
      const token = getToken();

      const response = await fetch(`${API_URL}/${codigo}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(dados),
      });

      if (!response.ok) {
        const erro = await response.text();
        throw new Error('Erro ao atualizar funcionário: ' + erro);
      }

      return await response.json();
    } catch (error) {
      console.error(error);
      throw error;
    }
  }

  async inativar(codigo: number): Promise<Funcionario> {
    try {
      const token = getToken();

      const response = await fetch(`${API_URL}/${codigo}`, {
        method: 'DELETE',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        const erro = await response.text();
        throw new Error('Erro ao inativar funcionário: ' + erro);
      }

      return await response.json();
    } catch (error) {
      console.error(error);
      throw error;
    }
  }
}

export const funcionarioService = new FuncionarioService();
