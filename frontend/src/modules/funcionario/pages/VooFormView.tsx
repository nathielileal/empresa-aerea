import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Box, Typography, Button, TextField, MenuItem, Select, InputLabel, FormControl } from "@mui/material";
import { ArrowBack } from "@mui/icons-material";
import { vooService } from "../services/vooService";
import { Aeroporto } from "../models/AeroportoTypes";

export default function VooFormView() {
    const [data, setData] = useState<string>("");
    const [aeroporto_origem, setOrigem] = useState<string>("");
    const [aeroporto_destino, setDestino] = useState<string>("");
    const [valor_passagem, setValorReais] = useState<string>("");
    const [quantidade_poltronas_total, setPoltronas] = useState<string>("");
    const [codigoGerado, setCodigoGerado] = useState<string | null>(null);
    const [aeroportos, setAeroportos] = useState<Aeroporto[]>([]);

    const navigate = useNavigate();

    useEffect(() => {
        const agora = new Date();
        const dataFormatada = new Date(agora.getTime() - agora.getTimezoneOffset() * 60000).toISOString().slice(0, 16);
        setData(dataFormatada);

        vooService.listarAeroportos().then(setAeroportos).catch(() => alert("Erro ao carregar aeroportos"));
    }, []);

    const handleSubmit = async () => {
        try {
            const dataFormatada = `${data}:00-03:00`;

            const response = await vooService.adicionar({
                data: dataFormatada,
                valor_passagem: parseFloat(valor_passagem),
                quantidade_poltronas_total: parseInt(quantidade_poltronas_total || "0"),
                quantidade_poltronas_ocupadas: 0,
                codigo_aeroporto_origem: aeroporto_origem,
                codigo_aeroporto_destino: aeroporto_destino,
            });

            setCodigoGerado(response.codigo);
            setOrigem("");
            setDestino("");
            setValorReais("");
            setPoltronas("");
        } catch (error) {
            alert(error instanceof Error ? error.message : "Erro inesperado");
        }
    };

    return (
        <Container>
            <Box display="flex" alignItems="center" justifyContent="space-between" marginBottom={2}>
                <Button onClick={() => navigate("/funcionario/voos")} size="small" sx={{ color: 'primary.main', textTransform: "none" }}>
                    <ArrowBack />
                </Button>
                <Typography variant="h5" gutterBottom sx={{ flexGrow: 1, textAlign: "center" }}>
                    Novo Voo
                </Typography>
            </Box>

            <Container maxWidth="sm">
                <TextField fullWidth label="Data/Hora" type="datetime-local" value={data} onChange={(e) => setData(e.target.value)} margin="normal" />

                <FormControl fullWidth margin="normal">
                    <InputLabel>Origem</InputLabel>
                    <Select value={aeroporto_origem} onChange={(e) => setOrigem(e.target.value)} label="Origem">
                        {aeroportos.map((aeroporto) => (
                            <MenuItem key={aeroporto.codigo} value={aeroporto.codigo}>
                                {aeroporto.nome} ({aeroporto.codigo})
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>

                <FormControl fullWidth margin="normal">
                    <InputLabel>Destino</InputLabel>
                    <Select value={aeroporto_destino} onChange={(e) => setDestino(e.target.value)} label="Destino">
                        {aeroportos.map((aeroporto) => (
                            <MenuItem key={aeroporto.codigo} value={aeroporto.codigo}>
                                {aeroporto.nome} ({aeroporto.codigo})
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>

                <TextField fullWidth label="Valor (R$)" type="number" value={valor_passagem} onChange={(e) => setValorReais(e.target.value)} margin="normal" />
                <TextField fullWidth label="Quantidade de Poltronas" type="number" value={quantidade_poltronas_total} onChange={(e) => setPoltronas(e.target.value)} margin="normal" />

                {valor_passagem && (
                    <Typography variant="body2" color="textSecondary" sx={{ marginTop: 1 }}>
                        Equivalente em milhas: {Math.floor(Number(valor_passagem) * 100)} milhas
                    </Typography>
                )}

                <Box marginTop={2}>
                    <Button variant="contained" fullWidth onClick={handleSubmit}>
                        Cadastrar
                    </Button>
                </Box>

                {codigoGerado && (
                    <Box marginTop={3} textAlign="center">
                        <Typography variant="h6" color="primary">
                            Voo cadastrado com código: {codigoGerado}
                        </Typography>
                    </Box>
                )}
            </Container>
        </Container>
    );
}
