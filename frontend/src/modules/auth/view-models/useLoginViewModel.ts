import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { authService } from '../services/authService';
import { useAuth } from '../../../shared/contexts/AuthContext';
import { AuthUser, LoginFormData, UserProfile } from '../models/AuthTypes';

// modules/auth/view-models/useLoginViewModel.ts
export function useLoginViewModel() {
  const { login: authLogin } = useAuth();
  const navigate = useNavigate();
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleLogin = async (formData: LoginFormData) => {
    setLoading(true);
    setError('');

    try {
      console.log("Iniciando o login")
      const { access_token, usuario, tipo } = await authService.login(formData);
      console.log("login realizado", access_token, usuario, tipo)
      const usuarioComTipo: AuthUser = {
        ...usuario,
        tipo: tipo as UserProfile
      };
      await authLogin(usuarioComTipo, access_token); // Espera a atualização do contexto
      navigate(tipo === 'CLIENTE' ? '/cliente/initial-page' : '/funcionario/initial-page');
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Erro no login');
    } finally {
      setLoading(false);
    }
  };

  return { error, loading, handleLogin };
}