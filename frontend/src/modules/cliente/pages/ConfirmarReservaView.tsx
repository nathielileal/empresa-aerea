import { Box, Typography, Paper, TextField, Button, Alert, Divider, useTheme, CircularProgress } from '@mui/material';
import { useReservaViewModel } from '../view-models/useReservaViewModel';
import { useNavigate } from 'react-router-dom';
import { ArrowBack } from '@mui/icons-material';

export function ConfirmarReservaView() {
    const theme = useTheme();
    const navigate = useNavigate();
    
    const {
        vooSelecionado,
        quantidade,
        setQuantidade,
        milhasUsadas,
        setMilhasUsadas,
        saldo_milhas,
        valorTotal,
        milhasTotais,
        valorComMilhas,
        finalizarReserva,
        loading,
        error
    } = useReservaViewModel();

    if (!vooSelecionado) {
        return null;
    }

    return (
        <Box sx={{ p: 4 }}>
            <Paper elevation={3} sx={{ p: 3 }}>
                <Box display="flex" justifyContent="space-between" marginBottom={2}>
                    <Button size="small" onClick={() => navigate('/cliente/reservar')} sx={{ color: 'primary.main', width: "fit-content", textTransform: "none" }}>
                        <ArrowBack />
                    </Button>

                    <Typography variant="h5" gutterBottom sx={{ flexGrow: 1, textAlign: "center" }}>
                        Confirmar Reserva
                    </Typography>

                </Box>

                <Divider sx={{ my: 1 }} />

                <Box sx={{ m: 3 }}>
                    <Typography variant="h6" >Detalhes do Voo</Typography>
                    <Box display="grid" gridTemplateColumns="repeat(2, 1fr)" gap={2}>
                        <Box>
                            <Typography><strong>Código:</strong> {vooSelecionado.codigo}</Typography>
                            <Typography><strong>Origem:</strong> {vooSelecionado.origem}</Typography>
                            <Typography><strong>Destino:</strong> {vooSelecionado.destino}</Typography>
                        </Box>
                        <Box>
                            <Typography><strong>Data/Hora:</strong> {new Date(vooSelecionado.dataHora).toLocaleString('pt-BR')}</Typography>
                            <Typography><strong>Preço unitário:</strong> R$ {vooSelecionado.valorReais.toFixed(2)}</Typography>
                            <Typography><strong>Milhas por passagem:</strong> {vooSelecionado.valorMilhas.toLocaleString()}</Typography>
                        </Box>
                    </Box>
                </Box>

                <Divider sx={{ my: 1 }} />

                <Box sx={{ m: 3 }}>
                    <Typography variant="h6">Informações da Reserva</Typography>
                    <TextField
                        label="Quantidade de passagens"
                        type="number"
                        value={quantidade}
                        onChange={(e) => setQuantidade(Math.max(1, parseInt(e.target.value) || 1))}
                        inputProps={{ min: 1, max: vooSelecionado.poltronas }}
                        sx={{ mb: 2 }}
                        fullWidth
                    />

                    <Box display="grid" gridTemplateColumns="repeat(2, 1fr)" gap={2}>
                        <Box>
                            <Typography><strong>Valor total:</strong></Typography>
                            <Typography variant="h6">R$ {valorTotal.toFixed(2)}</Typography>
                        </Box>
                        <Box>
                            <Typography><strong>Milhas necessárias:</strong></Typography>
                            <Typography variant="h6">{milhasTotais.toLocaleString()}</Typography>
                        </Box>
                    </Box>
                </Box>

                <Divider sx={{ my: 1 }} />

                <Box sx={{ m: 3 }}>
                    <Typography variant="h6">Pagamento com Milhas</Typography>

                    <Typography><strong>Saldo disponível:</strong> {saldo_milhas.toLocaleString()} milhas</Typography>

                    <TextField
                        label="Milhas a usar"
                        type="number"
                        value={milhasUsadas}
                        onChange={(e) => setMilhasUsadas(parseInt(e.target.value) || 0)}
                        inputProps={{
                            min: 0,
                            max: Math.min(saldo_milhas, milhasTotais),
                            step: 100
                        }}
                        sx={{ my: 2 }}
                        fullWidth
                        helperText={`Máximo: ${Math.min(saldo_milhas, milhasTotais).toLocaleString()} milhas`}
                    />

                    <Box display="grid" gridTemplateColumns="repeat(2, 1fr)" gap={2}>
                        <Box>
                            <Typography><strong>Valor a pagar:</strong></Typography>
                            <Typography variant="h6" color="primary">
                                R$ {valorComMilhas.toFixed(2)}
                            </Typography>
                        </Box>
                        <Box>
                            <Typography><strong>Milhas restantes:</strong></Typography>
                            <Typography variant="h6">
                                {(saldo_milhas - milhasUsadas).toLocaleString()}
                            </Typography>
                        </Box>
                    </Box>
                </Box>

                {error && (
                    <Alert severity="error" sx={{ mb: 2 }}>
                        {error}
                    </Alert>
                )}

                <Button
                    variant="contained"
                    color="primary"
                    onClick={finalizarReserva}
                    disabled={loading}
                    sx={{
                        borderRadius: theme.shape.borderRadius,
                        py: 2,
                        fontSize: '1.1rem'
                    }}
                    fullWidth
                >
                    {loading ? <CircularProgress size={24} color="inherit" /> : 'Confirmar'}
                </Button>
            </Paper>
        </Box>
    );
}