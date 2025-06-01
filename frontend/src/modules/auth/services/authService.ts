// modules/auth/services/authService.ts
import { AuthResponse, AuthUser, LoginFormData } from '../models/AuthTypes';
import { mockDatabase } from '../mocks/mockDatabase';

const API_URL = 'http://localhost:3000/auth';

export const authService = {
  async login(formData: LoginFormData): Promise<AuthResponse> {
    try {
      console.log("parametros recebidos para o login", formData)
      const response = await fetch(`${API_URL}/login`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
      });


      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Erro ao fazer login');
      }

      const data: AuthResponse = await response.json();
      console.log(response)

      if (!data.access_token) {
        throw new Error('Token de acesso não recebido');
      }
      // Armazena o token no localStorage
      localStorage.setItem('token', data.access_token);

      return data;

    } catch (error) {
      console.error('Erro no serviço de login:', error);

      if (error instanceof Error) {
        throw new Error(error.message || 'Erro ao processar login');
      }

      throw new Error('Erro desconhecido ao fazer login');
    }
  },

  async getCurrentUser(): Promise<AuthUser | null> {
    const userString = localStorage.getItem('user');
    if (!userString) {
      throw new Error('Usuário não encontrado no localStorage');
    }
    return JSON.parse(userString);
  },
  

  // async getCurrentUser(): Promise<AuthUser> {
  //   const access_token = localStorage.getItem('access_token');
  //   console.log(access_token)

  //   if (!access_token) {
  //     throw new Error('Não autenticado');
  //   }

  //   const userId = access_token.replace('mock-token-', '');
  //   const user = Object.values(mockDatabase).find(u => u.id === userId);

  //   if (!user) {
  //     localStorage.removeItem('access_token'); // Limpa token inválido
  //     throw new Error('Usuário não encontrado');
  //   }

  //   return {
  //     id: user.id,
  //     nome: user.nome,
  //     email: user.email,
  //     tipo: user.tipo
  //   };
  // },

  // async getCurrentUser(): Promise<AuthUser> {
  //   const token = localStorage.getItem('token');

  //   if (!token) {
  //     throw new Error('Não autenticado');
  //   }

  //   const response = await fetch(`${API_URL}/me`, {
  //     headers: {
  //       'Authorization': `Bearer ${token}`,
  //     },
  //   });

  //   if (!response.ok) {
  //     throw new Error('Não autenticado');
  //   }

  //   const data: AuthUser = await response.json();
  //   return data;
  // },


  async logout(): Promise<void> {
    localStorage.removeItem('access_token');
  }
};