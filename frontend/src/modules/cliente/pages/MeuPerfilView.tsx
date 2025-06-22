import { Container, Paper, TextField, Typography, Box } from "@mui/material";
import { usePerfilViewModel } from "../view-models/usePerfilViewModel";
import { Cliente } from "../models/ClienteTypes";

const MeuPerfilView = () => {
    const { user } = usePerfilViewModel()
    const cliente = user?.tipo === 'CLIENTE' ? user as Cliente : null;

    return (
        <Container sx={{ display: "flex", flexDirection: "column", justifyContent: "center", alignItems: "center", paddingY: 4 }} >
            <Paper sx={{ padding: 4, width: "100%", maxWidth: 800 }}>
                <Typography variant="h5" fontWeight="bold" gutterBottom>
                    Meu Perfil
                </Typography>

                <Box display="grid" gridTemplateColumns={{ xs: "1fr", sm: "1fr 1fr" }} gap={2}>
                    <TextField fullWidth label="Nome" value={cliente?.nome} InputProps={{ readOnly: true }} />
                    <TextField fullWidth label="CPF" value={cliente?.cpf} InputProps={{ readOnly: true }} />
                    <TextField fullWidth label="E-mail" value={cliente?.email} InputProps={{ readOnly: true }} />
                    <TextField fullWidth label="CEP" value={cliente?.endereco.cep} InputProps={{ readOnly: true }} />
                    <TextField fullWidth label="Rua" value={cliente?.endereco.rua} InputProps={{ readOnly: true }} />
                    <TextField fullWidth label="Número" value={cliente?.endereco.numero} InputProps={{ readOnly: true }} />
                    <TextField fullWidth label="Complemento" value={cliente?.endereco.complemento} InputProps={{ readOnly: true }} />
                    <TextField fullWidth label="Cidade" value={cliente?.endereco.cidade} InputProps={{ readOnly: true }} />
                    <TextField fullWidth label="Estado" value={cliente?.endereco.uf} InputProps={{ readOnly: true }} />
                </Box>
            </Paper>
        </Container>
    );
};

export default MeuPerfilView;
