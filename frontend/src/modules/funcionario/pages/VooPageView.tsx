import { useEffect, useState } from "react";
import { Voo } from "../models/VooTypes";
import { vooService } from "../services/vooService";
import {
  Container,
  Typography,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Button,
  Box,
  CircularProgress,
} from "@mui/material";
import { useNavigate } from "react-router-dom";

export default function VooPageView() {
  const [voos, setVoos] = useState<Voo[]>([]);

  const [loading, setLoading] = useState(true);

  const navigate = useNavigate();

  useEffect(() => {
    const carregarVoos = async () => {
      const resposta = await vooService.listar();
      setVoos(resposta);
      setLoading(false);
    };

    carregarVoos();
  }, []);

  const formatarData = (dataHora: string) => {
    if (!dataHora) return "Data inválida";

    const [data, hora] = dataHora.split("T");
    if (!data || !hora) return "Data inválida";

    const [ano, mes, dia] = data.split("-");
    const [horaFormatada, minuto] = hora.split(":");

    return `${dia}/${mes}/${ano} ${horaFormatada}:${minuto}`;
  };

  return (
    <Container maxWidth="md">
      <Box display="flex" justifyContent="space-between" alignItems="center" marginBottom={2}>
        <Typography variant="h5">Voos</Typography>
        <Button variant="contained" size="small" onClick={() => navigate("/funcionario/cadastro-voo")} sx={{ width: "fit-content", textTransform: "none" }}>
          Novo
        </Button>
      </Box>

      {loading ? (
        <Box display="flex" justifyContent="center" alignItems="center" height="50vh">
          <CircularProgress />
        </Box>
      ) : voos.length === 0 ? (
        <Typography variant="subtitle1" color="textSecondary">
          Nenhum voo cadastrado.
        </Typography>
      ) : (
        <TableContainer component={Paper}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>Código</TableCell>
                <TableCell>Origem</TableCell>
                <TableCell>Destino</TableCell>
                <TableCell>Data/Hora</TableCell>
                <TableCell>Valor (R$)</TableCell>
                <TableCell>Poltronas</TableCell>
                <TableCell>Situação</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {voos.map((v) => (
                <TableRow key={v.codigo}>
                  <TableCell>{v.codigo}</TableCell>
                  <TableCell>{v.aeroporto_origem.codigo}</TableCell>
                  <TableCell>{v.aeroporto_destino.codigo}</TableCell>
                  <TableCell>{formatarData(v.data)}</TableCell>
                  <TableCell>{v.valor_passagem.toFixed(2)}</TableCell>
                  <TableCell>{v.quantidade_poltronas_total}</TableCell>
                  <TableCell>{v.estado}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      )}
    </Container>
  );
}
