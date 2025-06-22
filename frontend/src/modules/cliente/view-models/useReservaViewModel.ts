import { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useAuth } from '../../../shared/contexts/AuthContext';
import { vooService } from '../services/vooService';
import { Voo } from '../../funcionario/models/VooTypes';
import { Reserva } from '../models/ReservaTypes';
import { Cliente } from '../models/ClienteTypes';
import { UserProfile } from '../../auth/models/AuthTypes';
import { clienteService } from '../services/clienteService';

export function useReservaViewModel() {
    const navigate = useNavigate();
    const { vooId } = useParams();
    const { user, updateUser } = useAuth();

    // Estados
    const [origem, setOrigem] = useState('');
    const [destino, setDestino] = useState('');
    const [voos, setVoos] = useState<Voo[]>([]);
    const [vooSelecionado, setVooSelecionado] = useState<Voo | null>(null);
    const [quantidade, setQuantidade] = useState(1);
    const [milhasUsadas, setMilhasUsadas] = useState(0);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [saldo_milhas, setSaldo_milhas] = useState(Number)
    // Calculados
    const cliente = user?.tipo === UserProfile.CLIENTE ? user as Cliente : null;
    const valorTotal = vooSelecionado ? vooSelecionado.valor_passagem * quantidade : 0;
    const milhasTotais = vooSelecionado ? (vooSelecionado.valor_passagem / 5) * quantidade : 0;
    const valorComMilhas = vooSelecionado ?
        Math.max(0, valorTotal - (milhasUsadas * vooSelecionado.valor_passagem / (vooSelecionado.valor_passagem / 5))) : 0;

    // Efeitos
    useEffect(() => {
        if (vooId) {
            carregarVooSelecionado(vooId);
        }
        getCliente()
    }, [vooId]);

    // Métodos
    const buscarVoos = async () => {
        setLoading(true);
        setError('');
        try {
            const voosEncontrados = await vooService.buscarVoos(origem, destino);
            setVoos(voosEncontrados);
        } catch (err) {
            setError('Erro ao buscar voos');
        } finally {
            setLoading(false);
        }
    };

    const getCliente = async () => {
        if (!user?.codigo) return;

        setLoading(true);
        try {
            const cliente = await clienteService.getById(user.codigo);
            setSaldo_milhas(cliente.saldo_milhas);
        } catch (err) {
            setError('Erro ao carregar dados do cliente');
        } finally {
            setLoading(false);
        }
    };

    const carregarVooSelecionado = async (id: string) => {
        setLoading(true);
        console.log('buscando voo')
        try {
            const voo = await vooService.buscarVooById(id);
            console.log("Voo encontrado", voo)
            if (voo) {
                setVooSelecionado(voo);
            } else {
                navigate('/cliente/reservar');
            }
        } catch (err) {
            setError('Erro ao carregar voo');
            navigate('/cliente/reservar');
        } finally {
            setLoading(false);
        }
    };

    const selecionarVoo = async (voo: Voo) => {
        console.log('Voo selecionado')
        await carregarVooSelecionado(voo.codigo)
        navigate(`/cliente/reservar/confirmar/${voo.codigo}`);
    };

    const atualizarMilhasUsadas = (value: number) => {
        if (!vooSelecionado) return;
        const maxMilhas = Math.min(saldo_milhas, milhasTotais);
        setMilhasUsadas(Math.min(value, maxMilhas));
    };

    const finalizarReserva = async () => {
        if (!vooSelecionado || !user || !cliente) return;

        setLoading(true);
        try {
            const payload = {
                valor: vooSelecionado.valor_passagem,
                milhas_utilizadas: milhasUsadas,
                quantidade_poltronas: quantidade,
                codigo_cliente: cliente?.codigo!.toString(),
                codigo_voo: vooSelecionado.codigo
            };

            console.log('Enviando reserva:', payload);

            const reserva = await vooService.finalizarReserva(payload);

            // Atualiza o estado local com novo saldo
            // updateUser({
            //     ...user,
            //     saldo_milhas: cliente.saldo_milhas - milhasUsadas
            // });

            navigate('/cliente/initial-page');
        } catch (err) {
            console.error(err);
            setError('Erro ao finalizar reserva');
        } finally {
            setLoading(false);
        }
    };

    return {
        // Estados
        origem,
        setOrigem,
        destino,
        setDestino,
        voos,
        vooSelecionado,
        quantidade,
        setQuantidade,
        milhasUsadas,
        setMilhasUsadas: atualizarMilhasUsadas,
        loading,
        error,

        // Calculados
        saldo_milhas,
        valorTotal,
        milhasTotais,
        valorComMilhas,

        // Métodos
        buscarVoos,
        selecionarVoo,
        finalizarReserva
    };
}