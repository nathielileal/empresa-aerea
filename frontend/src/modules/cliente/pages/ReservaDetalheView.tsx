import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableRow,
  Paper,
  Typography,
  CircularProgress,
  Alert,
  Chip,
  IconButton
} from '@mui/material';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import { useParams, useNavigate } from 'react-router-dom';
import { useReservaDetalheViewModel } from '../view-models/useReservaDetalheViewModel';

function ReservaDetalheView() {
  const { reservaId } = useParams<{ reservaId: string }>();
  const navigate = useNavigate();
  const { reserva, loading, error } = useReservaDetalheViewModel(reservaId || '');

  const handleVoltar = () => {
    navigate(-1);
  };

  return (
    <div style={{ padding: '24px', position: 'relative' }}>
      <IconButton onClick={handleVoltar} style={{ position: 'absolute', top: 8, left: 8, color: "primary.main" }} aria-label="Voltar">
        <ChevronLeftIcon />
      </IconButton>

      {loading && <CircularProgress />}
      {error && <Alert severity="error">{error}</Alert>}

      {reserva && (
        <>
          <Typography variant="h5" gutterBottom>
            Detalhes da Reserva
          </Typography>

          <TableContainer component={Paper}>
            <Table>
              <TableBody>
                <TableRow sx={{ backgroundColor: "primary.main" }}>
                  <TableCell sx={{ color: "white", fontWeight: "bold" }}><strong>Código</strong></TableCell>
                  <TableCell sx={{ color: "white", fontWeight: "bold" }}><strong>Data/Hora</strong></TableCell>
                  <TableCell sx={{ color: "white", fontWeight: "bold" }}><strong>Rota</strong></TableCell>
                  <TableCell sx={{ color: "white", fontWeight: "bold" }}><strong>Valor</strong></TableCell>
                  <TableCell sx={{ color: "white", fontWeight: "bold" }}><strong>Milhas utilizadas</strong></TableCell>
                  <TableCell sx={{ color: "white", fontWeight: "bold" }}><strong>Status</strong></TableCell>
                </TableRow>

                <TableRow>
                  <TableCell>{reserva.codigo}</TableCell>
                  <TableCell>
                    {new Date(reserva.data).toLocaleDateString('pt-BR', {
                      day: '2-digit',
                      month: 'long',
                      year: 'numeric',
                      hour: '2-digit',
                      minute: '2-digit'
                    })}
                  </TableCell>
                  <TableCell>{reserva.voo.aeroporto_origem.codigo} → {reserva.voo.aeroporto_destino.codigo}</TableCell>
                  <TableCell>R$ {reserva.valor.toFixed(2)}</TableCell>
                  <TableCell>{reserva.quantidade_milhas.toLocaleString()}</TableCell>
                  <TableCell>
                    <Chip
                      label={reserva.estado.toUpperCase()}
                      color={
                        reserva.estado === 'concluída'
                          ? 'success'
                          : reserva.estado === 'reservada'
                            ? 'warning'
                            : 'error'
                      }
                      variant="outlined"
                    />
                  </TableCell>
                </TableRow>
              </TableBody>
            </Table>
          </TableContainer>
        </>
      )}
    </div>
  );
}

export default ReservaDetalheView;
