import { RegisterFormData, UserProfile } from '../models/AuthTypes';
import { clienteService } from '../../cliente/services/clienteService';
import { viacepService } from '../services/viacepService';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { Cliente } from '../../cliente/models/ClienteTypes';
import { authService } from '../services/authService';

export function useRegisterViewModel() {
  const navigate = useNavigate();
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState(false);
  const [successMessage, setSuccessMessage] = useState('');

  const handleRegister = async (formData: RegisterFormData) => {
    setLoading(true);
    setError('');

    try {
      console.log("iniciando autocadastro")
      const enderecoData = await viacepService.buscarEndereco(formData.cep);
      console.log(enderecoData)
      const cliente = {
        nome: formData.nome,
        cpf: formData.cpf,
        email: formData.email,
        saldoMilhas: 0.0,
        // perfil: UserProfile.CLIENTE,
        ativo: true,
        endereco: {
          cep: formData.cep,
          uf: enderecoData.uf,
          cidade: enderecoData.cidade,
          bairro: enderecoData.bairro,
          rua: enderecoData.logradouro,
          numero: enderecoData.numero,
          complemento: enderecoData.complemento || '',
        },
      };
      await clienteService.cadastrar(cliente);
      setSuccess(true);
      setSuccessMessage(`Cadastro realizado com sucesso! Redirecionando para login...`);

      setTimeout(() => {
        navigate('/login');
      }, 3000);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Erro no cadastro');
    } finally {
      setLoading(false);
    }
  };

  return { error, loading, success, successMessage, handleRegister };
}