// components/ConfirmarEmbarqueModal.tsx
import React, { useState } from 'react';
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Button,
  Typography,
} from '@mui/material';
import { Reserva } from '../../cliente/models/ReservaTypes';
import { reservaService } from '../../cliente/services/reservaService';

interface ConfirmarEmbarqueModalProps {
  open: boolean;
  onClose: () => void;
  vooSelecionado: { dataHora: string; origem: string; destino: string };
  reservas: Reserva[];
  onConfirmar: (reservaAtualizada: Reserva) => void;
}

const ConfirmarEmbarqueModal: React.FC<ConfirmarEmbarqueModalProps> = ({
  open,
  onClose,
  vooSelecionado,
  onConfirmar,
}) => {
  const [codigo, setCodigo] = useState('');
  const [erro, setErro] = useState('');

  const handleConfirmar = async () => {
    const reservas = await reservaService.getReservas('', {
      estados: ['CHECK-IN'],
    });
  
    const reserva = reservas.find(r => r.codigo === codigo);
  
    if (!reserva) {
      return setErro('Reserva não encontrada ou não está em CHECK-IN.');
    }
  
    const vooCondiz =
      reserva.data === vooSelecionado.dataHora &&
      reserva.voo.aeroporto_origem.codigo === vooSelecionado.origem &&
      reserva.voo.aeroporto_destino.codigo === vooSelecionado.destino;
  
    if (!vooCondiz) {
      return setErro('Reserva não corresponde a este voo.');
    }
  
    await reservaService.atualizarEstadoReserva(reserva.codigo, 'EMBARCADA');
    onConfirmar({ ...reserva, estado: 'EMBARCADA' });
  
    setErro('');
    setCodigo('');
    onClose();
  };

  return (
    <Dialog open={open} onClose={onClose}>
      <DialogTitle>Confirmar Embarque</DialogTitle>
      <DialogContent>
        <TextField
          fullWidth
          label="Código da Reserva"
          value={codigo}
          onChange={(e) => setCodigo(e.target.value)}
          sx={{ mt: 2 }}
        />
        {erro && <Typography color="error" sx={{ mt: 1 }}>{erro}</Typography>}
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>Cancelar</Button>
        <Button variant="contained" color="primary" onClick={handleConfirmar}>
          Confirmar
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default ConfirmarEmbarqueModal;
