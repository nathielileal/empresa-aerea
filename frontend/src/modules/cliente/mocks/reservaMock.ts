// Dados mockados
import { Reserva } from "../models/ReservaTypes";
export const reservasMock: Reserva[] = [
    {
      id: '1',
      codigo: 'RES-2023-001',
      dataHora: '2023-12-10T14:30:00Z',
      origem: 'GRU (São Paulo)',
      destino: 'GIG (Rio de Janeiro)',
      valorReais: 350.00,
      milhasGastas: 5000,
      estado: 'CRIADA'
    },
    {
      id: '2',
      codigo: 'RES-2023-002',
      dataHora: '2023-11-15T08:45:00Z',
      origem: 'BSB (Brasília)',
      destino: 'REC (Recife)',
      valorReais: 420.00,
      milhasGastas: 7000,
      estado: 'REALIZADA'
    },
    {
      id: '3',
      codigo: 'RES-2025-005',
      dataHora: new Date(Date.now() + 1 * 60 * 60 * 1000).toISOString(), // 1 hora a partir de agora
      origem: 'GRU (São Paulo)',
      destino: 'POA (Porto Alegre)',
      valorReais: 350.00,
      milhasGastas: 6000,
      estado: 'CRIADA'
    }
    
  ];
