import { Reserva, EstadoReserva } from '../models/ReservaTypes';
import { reservasMock } from '../mocks/reservaMock';
import { vooService } from './vooService';

export const reservaService = {
  // async getReservas(clienteId: string, filtros?: any): Promise<Reserva[]> {
  //   await new Promise(resolve => setTimeout(resolve, 500));
  //   return reservasMock.filter(r => 
  //     !filtros?.estados || filtros.estados.includes(r.estado)
  //   );
  // },

  async getReservas(clienteId: string, filtros?: any): Promise<Reserva[]> {
    try {
        console.log("chamando backend")
        const token = localStorage.getItem('access_token');

        const response = await fetch(`http://localhost:3000/clientes/${clienteId}/reservas`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`,
            },
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Erro ao buscar voos: ${errorText}`);
        }

        const reservas: Reserva[] = await response.json();
        console.log(reservas)
        return reservas;

    } catch (error) {
        console.error(error);
        throw error;
    }
},


  // async getReservaDetalhes(reservaId: string): Promise<Reserva> {
  //   await new Promise(resolve => setTimeout(resolve, 500));
  //   const reserva = reservasMock.find(r => r.codigo === reservaId);
  //   if (!reserva) throw new Error('Reserva não encontrada');
  //   return reserva;
  // },

  async getReservaDetalhes(reservaId: string): Promise<Reserva> {
    try {
        const token = localStorage.getItem('access_token');

        const response = await fetch(`http://localhost:3000/reservas/${reservaId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`,
            },
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Erro ao buscar voos: ${errorText}`);
        }

        const reserva: Reserva = await response.json();
        console.log(reserva)
        return reserva;

    } catch (error) {
        console.error(error);
        throw error;
    }
},

  async cancelarReserva(reservaId: string): Promise<void> {
    await new Promise(resolve => setTimeout(resolve, 500));
    const reserva = reservasMock.find(r => r.codigo === reservaId);
    if (reserva) reserva.estado = 'CANCELADA';
  },

  async atualizarEstadoReserva(id: string, novoEstado: EstadoReserva): Promise<void> {
    await new Promise(resolve => setTimeout(resolve, 500));
    const reserva = reservasMock.find(r => r.codigo === id);
    if (reserva) reserva.estado = novoEstado;
  },

  async cancelarReservasPorVoo(codigoVoo: string): Promise<void> {
    const voos = await vooService.listar();
    const voo = voos.find((v) => v.codigo === codigoVoo);

    if (!voo) {
      throw new Error("Voo não encontrado");
    }

    if (voo.estado !== "CONFIRMADO") {
      throw new Error("Somente voos confirmados podem ser cancelados");
    }

    voo.estado = "CANCELADO";
    await vooService.adicionar(voo);  

    const reservas = reservasMock.filter(r => r.codigo === voo.codigo);
    reservas.forEach(reserva => {
      reserva.estado = "CANCELADA";  // Altera o estado das reservas
    });

    localStorage.setItem("reservas", JSON.stringify(reservasMock));
  }
};
