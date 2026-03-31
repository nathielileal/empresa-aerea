// modules/auth/components/RegisterForm.tsx
import { TextField, Button, CircularProgress } from '@mui/material';
import { useState } from 'react';
import { RegisterFormData } from '../models/AuthTypes';

interface Props {
  onSubmit: (data: RegisterFormData) => void;
  loading: boolean;
  error: string;
}

export function RegisterForm({ onSubmit, loading, error }: Props) {
  const [formData, setFormData] = useState<RegisterFormData>({
    cpf: '',
    nome: '',
    email: '',
    cep: '',
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit(formData);
  };

  return (
    <form onSubmit={handleSubmit}>
      <TextField name="cpf" label="CPF" fullWidth onChange={handleChange} />
      <TextField name="nome" label="Nome" fullWidth onChange={handleChange} />
      <TextField name="email" label="E-mail" fullWidth onChange={handleChange} />
      <TextField name="cep" label="CEP" fullWidth onChange={handleChange} />

      {error && <p style={{ color: 'red' }}>{error}</p>}
      <Button type="submit" fullWidth variant="contained" disabled={loading}>
        {loading ? <CircularProgress size={24} /> : 'Cadastrar'}
      </Button>
    </form>
  );
}
