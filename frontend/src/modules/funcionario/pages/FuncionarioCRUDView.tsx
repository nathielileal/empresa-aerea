import React, { useState } from 'react';
import {
  Box,
  Button,
  TextField,
  Typography,
  Table,
  TableHead,
  TableBody,
  TableCell,
  TableRow,
  Paper,
  Alert,
  CircularProgress,
} from '@mui/material';
import { useFuncionarioViewModel } from '../view-models/useFuncionarioViewModel';

const FuncionarioCRUDView: React.FC = () => {
  const {
    funcionario,
    loading,
    error,
    criarFuncionario,
    atualizarFuncionario,
    removerFuncionario,
  } = useFuncionarioViewModel();

  const [activeTab, setActiveTab] = useState<'list' | 'create' | 'update'>('list');

  const [newEmployee, setNewEmployee] = useState({
    nome: '',
    cpf: '',
    email: '',
    telefone: '',
  });

  const [updateId, setUpdateId] = useState<number | null>(null);
  const [updateData, setUpdateData] = useState({
    nome: '',
    email: '',
    telefone: '',
  });

  const isValidName = (name: string) => /^[A-Za-zÀ-ÖØ-öø-ÿ\s]+$/.test(name);
  const isValidEmail = (email: string) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
  const isValidPhone = (phone: string) => /^[\d\s\+\-\(\)]+$/.test(phone);

  const handleCreate = async (e: React.FormEvent) => {
    e.preventDefault();
    const { nome, cpf, email, telefone } = newEmployee;

    if (!nome || !cpf || !email || !telefone) {
      alert('Preencha todos os campos para inserir um funcionário.');
      return;
    }
    
    if (!isValidName(nome)) {
      alert('O nome deve conter apenas letras e espaços.');
      return;
    }
    
    if (!isValidEmail(email)) {
      alert('Email inválido.');
      return;
    }
    
    if (!isValidPhone(telefone)) {
      alert('Telefone inválido. Use apenas dígitos, espaços, +, -, ().');
      return;
    }
    
    if (funcionario.some(emp => emp.cpf === cpf)) {
      alert('CPF já cadastrado.');
      return;
    }

    await criarFuncionario({ nome, cpf, email, telefone });
    
    setNewEmployee({ nome: '', cpf: '', email: '', telefone: '' });
    setActiveTab('list');
  };

  const handleUpdate = async (e: React.FormEvent) => {
    e.preventDefault();
   
    if (updateId === null) {
      alert('Informe o ID do funcionário a ser alterado.');
      return;
    }
    
    const { nome, email, telefone } = updateData;

    if (nome && !isValidName(nome)) {
      alert('O nome deve conter apenas letras e espaços.');
      return;
    }
   
    if (email && !isValidEmail(email)) {
      alert('Email inválido.');
      return;
    }
    
    if (telefone && !isValidPhone(telefone)) {
      alert('Telefone inválido.');
      return;
    }

    await atualizarFuncionario(updateId, { nome, email, telefone });
    
    setUpdateId(null);
    setUpdateData({ nome: '', email: '', telefone: '' });
    setActiveTab('list');
  };

  return (
    <Box sx={{ padding: 2 }}>
      <Typography variant="h4" sx={{ mb: 2 }}>Gestão de Funcionários</Typography>

      <Box sx={{ display: 'flex', gap: 2, mb: 4 }}>
        <Button variant="contained" onClick={() => setActiveTab('create')}>Inserir Funcionário</Button>
        <Button variant="contained" onClick={() => setActiveTab('update')}>Alterar Funcionário</Button>
        <Button variant="contained" onClick={() => setActiveTab('list')}>Lista de Funcionários</Button>
      </Box>

      {loading && <CircularProgress />}

      {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}

      {activeTab === 'create' && (
        <Box component="form" onSubmit={handleCreate} sx={{ mb: 4, maxWidth: 400 }}>
          <Typography variant="h6" sx={{ mb: 2 }}>Inserir Funcionário</Typography>
          <TextField
            label="Nome"
            value={newEmployee.nome}
            onChange={e => setNewEmployee({ ...newEmployee, nome: e.target.value })}
            fullWidth
            sx={{ mb: 2 }}
          />
          <TextField
            label="CPF"
            value={newEmployee.cpf}
            onChange={e => setNewEmployee({ ...newEmployee, cpf: e.target.value })}
            fullWidth
            sx={{ mb: 2 }}
          />
          <TextField
            label="Email"
            value={newEmployee.email}
            onChange={e => setNewEmployee({ ...newEmployee, email: e.target.value })}
            fullWidth
            sx={{ mb: 2 }}
          />
          <TextField
            label="Telefone"
            value={newEmployee.telefone}
            onChange={e => setNewEmployee({ ...newEmployee, telefone: e.target.value })}
            fullWidth
            sx={{ mb: 2 }}
          />
          <Button variant="contained" type="submit">Inserir Funcionário</Button>
        </Box>
      )}

      {activeTab === 'update' && (
        <Box component="form" onSubmit={handleUpdate} sx={{ mb: 4, maxWidth: 400 }}>
          <Typography variant="h6" sx={{ mb: 2 }}>Alterar Funcionário</Typography>
          <TextField
            label="ID do Funcionário"
            type="number"
            value={updateId ?? ''}
            onChange={e => setUpdateId(e.target.value ? Number(e.target.value) : null)}
            fullWidth
            sx={{ mb: 2 }}
          />
          <TextField
            label="Nome"
            value={updateData.nome}
            onChange={e => setUpdateData({ ...updateData, nome: e.target.value })}
            fullWidth
            sx={{ mb: 2 }}
          />
          <TextField
            label="Email"
            value={updateData.email}
            onChange={e => setUpdateData({ ...updateData, email: e.target.value })}
            fullWidth
            sx={{ mb: 2 }}
          />
          <TextField
            label="Telefone"
            value={updateData.telefone}
            onChange={e => setUpdateData({ ...updateData, telefone: e.target.value })}
            fullWidth
            sx={{ mb: 2 }}
          />
          <Button variant="contained" type="submit">Alterar Funcionário</Button>
        </Box>
      )}

      {activeTab === 'list' && (
        <>
          <Typography variant="h6" sx={{ mb: 2 }}>Lista de Funcionários</Typography>
          <Paper>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>ID</TableCell>
                  <TableCell>Nome</TableCell>
                  <TableCell>CPF</TableCell>
                  <TableCell>Email</TableCell>
                  <TableCell>Telefone</TableCell>
                  <TableCell>Status</TableCell>
                  <TableCell>Ações</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {funcionario.map(emp => (
                  <TableRow key={emp.codigo}>
                    <TableCell>{emp.codigo}</TableCell>
                    <TableCell>{emp.nome}</TableCell>
                    <TableCell>{emp.cpf}</TableCell>
                    <TableCell>{emp.email}</TableCell>
                    <TableCell>{emp.telefone}</TableCell>
                    <TableCell>{emp.ativo ? 'Ativo' : 'Inativo'}</TableCell>
                    <TableCell>
                      {emp.ativo && (
                        <Button
                          variant="contained"
                          color="error"
                          size="small"
                          onClick={() => {
                            if (window.confirm('Confirma inativar este funcionário?')) {
                              removerFuncionario(emp.codigo);
                            }
                          }}
                        >
                          Remover
                        </Button>
                      )}
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </Paper>
        </>
      )}
    </Box>
  );
};

export default FuncionarioCRUDView;
