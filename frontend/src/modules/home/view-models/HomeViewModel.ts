import { useNavigate } from 'react-router-dom';
import { HomeContent } from '../models/HomeTypes';

export function useHomeViewModel() {
  const navigate = useNavigate();

  // Dados mockados - substitua por chamada API se necessário
  const homeContent: HomeContent = {
    title: 'Bem-vindo ao Nosso Sistema',
    description: 'Gerencie suas reservas de voo de forma fácil e rápida',
    features: [
      'Reserve voos em poucos cliques',
      'Acompanhe suas reservas',
      'Gerencie seu perfil'
    ]
  };

  const navigateTo = (path: string) => {
    navigate(path);
  };

  return {
    homeContent,
    navigateTo
  };
}