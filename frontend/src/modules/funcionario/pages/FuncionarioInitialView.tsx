import { useState } from "react";
import { Box, Typography, Button, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, TextField, Dialog, DialogTitle, DialogContent, DialogActions, Alert } from "@mui/material";
import { VooViewModel } from "../view-models/useVooViewModel";

export default function FuncionarioInitialView() {
  const {
    flights,
    loading,
    error,
    confirmarEmbarque,
    cancelarVoo,
    realizarVoo,
  } = VooViewModel();

  const [modalOpen, setModalOpen] = useState(false);
  const [reservaCodigo, setReservaCodigo] = useState("");
  const [modalErro, setModalErro] = useState("");

  const abrirModalEmbarque = (): void => {
    setReservaCodigo("");
    setModalErro("");
    setModalOpen(true);
  };

  const fecharModal = () => {
    setModalOpen(false);
  };

  const onConfirmarEmbarque = async () => {
    try {
      await confirmarEmbarque(reservaCodigo);
      setModalOpen(false);
      alert("Embarque confirmado com sucesso!");
    } catch (err: any) {
      setModalErro(err.message || "Erro ao confirmar embarque");
    }
  };

  return (
    <Box sx={{ p: 2 }}>
      <Typography variant="h6" sx={{ mb: 2 }}>
        Voos para as Próximas 48h
      </Typography>

      {loading && <Typography>Carregando voos...</Typography>}
      {error && <Typography color="error">{error}</Typography>}

      {!loading && !error && flights.length === 0 && (
        <Typography>Nenhum voo para as próximas 48h.</Typography>
      )}

      {!loading && !error && flights.length > 0 && (
        <TableContainer component={Paper}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>Data/Hora</TableCell>
                <TableCell>Origem</TableCell>
                <TableCell>Destino</TableCell>
                <TableCell>Situação</TableCell>
                <TableCell>Ações</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {flights.map((flight) => (
                <TableRow key={flight.codigo}>
                  <TableCell>
                    {new Date(flight.data).toLocaleString()}
                  </TableCell>
                  <TableCell>{flight.aeroporto_origem.codigo}</TableCell>
                  <TableCell>{flight.aeroporto_destino.codigo}</TableCell>
                  <TableCell>{flight.estado}</TableCell>
                  <TableCell>
                    <Box sx={{ display: "flex", gap: 1 }}>
                      <Button
                        variant="contained"
                        size="small"
                        color="primary"
                        onClick={() => abrirModalEmbarque()}
                      >
                        Confirmar Embarque
                      </Button>
                      <Button
                        variant="contained"
                        size="small"
                        color="error"
                        disabled={flight.estado !== "CONFIRMADO"}
                        onClick={() => cancelarVoo(flight.codigo ?? '')}
                      >
                        Cancelar Voo
                      </Button>
                      <Button
                        variant="contained"
                        size="small"
                        color="success"
                        disabled={flight.estado !== "CONFIRMADO"}
                        onClick={() => realizarVoo(flight.codigo ?? '')}
                      >
                        Realizar Voo
                      </Button>
                    </Box>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      )}

      <Dialog open={modalOpen} onClose={fecharModal}>
        <DialogTitle>Confirmar Embarque</DialogTitle>
        <DialogContent>
          <TextField
            label="Código da Reserva"
            fullWidth
            value={reservaCodigo}
            onChange={(e) => setReservaCodigo(e.target.value)}
            autoFocus
          />
          {modalErro && <Alert severity="error" sx={{ mt: 2 }}>{modalErro}</Alert>}
        </DialogContent>
        <DialogActions>
          <Button onClick={fecharModal}>Cancelar</Button>
          <Button onClick={onConfirmarEmbarque} variant="contained" color="primary">
            Confirmar
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
}
