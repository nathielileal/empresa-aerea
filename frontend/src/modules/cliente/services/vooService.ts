import { Voo } from '../../funcionario/models/VooTypes';
import { reservasMock } from '../mocks/reservaMock';
import { voosMock } from '../mocks/voosMock';
import { Reserva } from '../models/ReservaTypes';

export const vooService = {
    async listar(): Promise<Voo[]> {
        return voosMock;
    },

    // async mockBuscarVoos(origem: string, destino: string): Promise<Voo[]> {
    //     return voosMock.filter(voo =>
    //         (!origem || voo.origem.includes(origem)) &&
    //         (!destino || voo.destino.includes(destino))
    //     );
    // },

    async buscarVoos(origem?: string, destino?: string, data?: string): Promise<Voo[]> {
        try {
            console.log("chamando backend")
            const token = localStorage.getItem('access_token');

            const params = new URLSearchParams();
            if (origem) params.append('origem', origem);
            if (destino) params.append('destino', destino);
            if (data) params.append('data', data); // precisa estar no formato ISO: '2025-08-10T00:00:00Z'

            const response = await fetch(`http://localhost:3000/voos?${params.toString()}`, {
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

            const voos: Voo[] = await response.json();
            console.log(voos)
            return voos;

        } catch (error) {
            console.error(error);
            throw error;
        }
    },

    async buscarVooById(id: string): Promise<Voo> {
        try {
            console.log("Buscando", id);

            const token = localStorage.getItem('access_token'); // pega o token

            const response = await fetch(`http://localhost:3000/voos/${id}`, {
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

            const voo: Voo = await response.json();
            return voo;

        } catch (error) {
            console.log(error);
            throw error;
        }
    },

    async finalizarReserva(payload: {
        valor: number;
        milhas_utilizadas: number;
        quantidade_poltronas: number;
        codigo_cliente: string;
        codigo_voo: string;
    }) {
        console.log("Fazendo reserva", payload)
        const response = await fetch('http://localhost:3000/reservas', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
        });

        if (!response.ok) {
            throw new Error('Erro ao finalizar reserva no backend');
        }

        return response.json();
    }
    ,


    async mockFinalizarReserva(dados: Reserva): Promise<Reserva> {
        reservasMock.push(dados);
        return dados;
    },

    async cancelarVoo(codigoVoo: string): Promise<void> {
        const voo = voosMock.find((v) => v.codigo === codigoVoo);

        if (!voo) {
            throw new Error("Voo não encontrado");
        }

        if (voo.estado !== "CONFIRMADO") {
            throw new Error("Somente voos confirmados podem ser cancelados");
        }

        voo.estado = "CANCELADO";

        const reservasAssociadas = reservasMock.filter(r => r.codigo === voo.codigo);
        reservasAssociadas.forEach(reserva => {
            reserva.estado = "CANCELADA";
        });

        const vooIndex = voosMock.findIndex(v => v.codigo === codigoVoo);
        voosMock[vooIndex] = voo;

        localStorage.setItem("reservas", JSON.stringify(reservasMock));
        localStorage.setItem("voos", JSON.stringify(voosMock));
    },

    async adicionar(voo: Voo): Promise<void> {
        voosMock.push(voo);
        localStorage.setItem("voos", JSON.stringify(voosMock));
    },

    async realizarVoo(codigoVoo: string): Promise<void> {
        const voo = voosMock.find((v) => v.codigo === codigoVoo);

        if (!voo) {
            throw new Error("Voo não encontrado");
        }

        if (voo.estado !== "CONFIRMADO") {
            throw new Error("Somente voos confirmados podem ser realizados");
        }

        // Atualiza o voo para o estado REALIZADO
        voo.estado = "REALIZADO";

        // Atualiza as reservas associadas ao voo
        const reservasAssociadas = reservasMock.filter(r => r.codigo === voo.codigo);
        reservasAssociadas.forEach(reserva => {
            if (reserva.estado === "EMBARCADA") {
                reserva.estado = "REALIZADA";
            } else if (reserva.estado !== "REALIZADA") {
                reserva.estado = "NÃO REALIZADA";
            }
        });

        // Atualiza o voo e as reservas no localStorage
        const vooIndex = voosMock.findIndex(v => v.codigo === codigoVoo);
        voosMock[vooIndex] = voo;
        localStorage.setItem("voos", JSON.stringify(voosMock));

        localStorage.setItem("reservas", JSON.stringify(reservasMock));
    }
};
