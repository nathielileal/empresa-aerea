import { useState } from 'react';
import { TextField, Button, Box, Snackbar, Alert } from '@mui/material';
import { LoginFormData } from '../models/AuthTypes';

interface LoginFormProps {
  onSubmit: (data: LoginFormData) => void;
  error?: string;
  loading: boolean;
}

export function LoginForm({ onSubmit, error, loading }: LoginFormProps) {
  const [formData, setFormData] = useState<LoginFormData>({
    email: '',
    senha: ''
  });
  const [, setOpenSnackbar] = useState<boolean>(!!error);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit(formData);
  };

  return (
    <Box component="form" onSubmit={handleSubmit}>
      <TextField
        error={!!error}
        fullWidth
        label="E-mail"
        variant="outlined"
        type="email"
        value={formData.email}
        onChange={(e) => setFormData({ ...formData, email: e.target.value })}
      />

      <TextField
        error={!!error}
        fullWidth
        label="Senha"
        variant="outlined"
        type="password"
        value={formData.senha}
        onChange={(e) => setFormData({ ...formData, senha: e.target.value })}
      />

      <Button
        fullWidth
        type="submit"
        variant="contained"
        disabled={loading}
      >
        {loading ? 'Carregando...' : 'Entrar'}
      </Button>

      <Snackbar
        open={!!error}
        autoHideDuration={6000}
        onClose={() => setOpenSnackbar(false)}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
      >
        <Alert severity="error" onClose={() => setOpenSnackbar(false)}>
          {error}
        </Alert>
      </Snackbar>
    </Box>
  );
}
