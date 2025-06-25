import { useState, useEffect } from 'react';
import { Funcionario } from '../models/FuncionarioTypes';
import { funcionarioService } from '../services/funcionarioService';

export function useFuncionarioViewModel() {
    const [funcionario, setFuncionario] = useState<Funcionario[]>([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const listarFuncionario = async () => {
        setLoading(true);
        setError(null);

        try {
            const lista = await funcionarioService.listar();
            const sorted = lista.sort((a, b) => a.nome.localeCompare(b.nome));
            
            setFuncionario(sorted);
        } catch (err: any) {
            setError(err.message || 'Erro desconhecido');
        } finally {
            setLoading(false);
        }
    };

    const criarFuncionario = async (funcionario: Omit<Funcionario, 'codigo' | 'ativo'>) => {
        setLoading(true);
        setError(null);
        
        try {
            await funcionarioService.criar(funcionario);
            
            await listarFuncionario();
        } catch (err: any) {
            setError(err.message || 'Erro desconhecido');
        } finally {
            setLoading(false);
        }
    };

    const atualizarFuncionario = async (codigo: number,data: Partial<Omit<Funcionario, 'codigo' | 'cpf' | 'ativo'>>) => {
        setLoading(true);
        setError(null);
        
        try {
            await funcionarioService.atualizar(codigo, data);
            
            await listarFuncionario();
        } catch (err: any) {
            setError(err.message || 'Erro desconhecido');
        } finally {
            setLoading(false);
        }
    };

    const removerFuncionario = async (codigo: number) => {
        setLoading(true);
        setError(null);
        
        try {
            await funcionarioService.inativar(codigo);
            
            await listarFuncionario();
        } catch (err: any) {
            setError(err.message || 'Erro desconhecido');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        listarFuncionario();
    }, []);

    return {
        funcionario,
        loading,
        error,
        listarFuncionario,
        criarFuncionario,
        atualizarFuncionario,
        removerFuncionario,
    };
}
