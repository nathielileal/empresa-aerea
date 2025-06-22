import { Table, TableHead, TableRow, TableCell, TableBody, TableContainer, Paper, Button, Typography, Box } from "@mui/material";
import { Reserva } from '../models/ReservaTypes';

interface Props {
  reservas: Reserva[];
  saldoMilhas?: number;
  onVerDetalhes: (id: string) => void;
  onCancelar: (id: string) => void;
}

export function TabelaReservas({
  reservas,
  saldoMilhas,
  onVerDetalhes,
  onCancelar
}: Props) {
  const formatarData = (dataISO: string) => {
    return new Date(dataISO).toLocaleDateString('pt-BR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  return (
    <Box>
      {saldoMilhas !== undefined && (
        <Typography variant="h6" sx={{ mb: 2 }}>
          <strong>Saldo de Milhas:</strong> {saldoMilhas.toLocaleString()}
        </Typography>
      )}

      <TableContainer component={Paper} sx={{ boxShadow: 3 }}>
        <Table>
          <TableHead>
            <TableRow sx={{ backgroundColor: "primary.main" }}>
              <TableCell sx={{ color: "white", fontWeight: "bold" }}>Código</TableCell>
              <TableCell sx={{ color: "white", fontWeight: "bold" }}>Data/Hora</TableCell>
              <TableCell sx={{ color: "white", fontWeight: "bold" }}>Origem</TableCell>
              <TableCell sx={{ color: "white", fontWeight: "bold" }}>Destino</TableCell>
              <TableCell sx={{ color: "white", fontWeight: "bold" }}>Ações</TableCell>
            </TableRow>
          </TableHead>

          <TableBody>
            {reservas.length > 0 ? (
              reservas.map((reserva) => (
                <TableRow key={reserva.codigo}>
                  <TableCell>{reserva.codigo}</TableCell>
                  <TableCell>{formatarData(reserva.data)}</TableCell>
                  <TableCell>{reserva.voo.aeroporto_origem.codigo}</TableCell>
                  <TableCell>{reserva.voo.aeroporto_destino.codigo}</TableCell>
                  <TableCell sx={{ display:"flex" }}>
                    <Button variant="outlined" size="small" sx={{ backgroundColor:"primary.main" }} onClick={() => onVerDetalhes(reserva.codigo)}>
                      Ver
                    </Button>
                    {reserva.estado === "CRIADA" /*|| reserva.estado === "CHECK-IN"*/ && (
                      <Button variant="contained" size="small" color="error" onClick={() => onCancelar(reserva.codigo)}>
                        Cancelar
                      </Button>
                    )}
                  </TableCell>
                </TableRow>
              ))
            ) : (
              <TableRow>
                <TableCell colSpan={6} align="center">
                  Nenhuma reserva encontrada
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  );
}