import { Box, Button, Typography, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, useTheme, Alert, CircularProgress, FormControl, InputLabel, Select, MenuItem, OutlinedInput, IconButton } from '@mui/material';
import { useReservaViewModel } from '../view-models/useReservaViewModel';
import aeroportos from "../../auth/components/airport.json";
import CloseIcon from "@mui/icons-material/Close";

export function BuscaVoosView() {
    const theme = useTheme();
    const {
        origem,
        setOrigem,
        destino,
        setDestino,
        voos,
        buscarVoos,
        selecionarVoo,
        loading,
        error
    } = useReservaViewModel();

    return (
        <Box sx={{ p: 4 }}>
            <Paper elevation={3} sx={{ p: 3, mb: 3 }}>
                <Typography variant="h5" >
                    Buscar Voos
                </Typography>

                <Box sx={{ display: 'flex', gap: 2, mb: 2 }}>
                    <FormControl fullWidth variant="outlined">
                        <InputLabel>Origem</InputLabel>
                        <Select
                            value={origem}
                            onChange={(e) => setOrigem(e.target.value)}
                            input={
                                <OutlinedInput
                                    label="Origem"
                                    endAdornment={
                                        origem ? (
                                            <IconButton
                                                size="small"
                                                onClick={(e) => {
                                                    e.stopPropagation();
                                                    setOrigem("");
                                                }}
                                                edge="end"
                                                sx={{ mr: 1 }}
                                            >
                                                <CloseIcon fontSize="small" />
                                            </IconButton>
                                        ) : null
                                    }
                                />
                            }
                        >
                            {aeroportos.map((aeroporto) => (
                                <MenuItem key={aeroporto.codigoIATA} value={aeroporto.codigoIATA}>
                                    {aeroporto.nome} ({aeroporto.codigoIATA})
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>

                    <FormControl fullWidth variant="outlined">
                        <InputLabel>Destino</InputLabel>
                        <Select
                            value={destino}
                            onChange={(e) => setDestino(e.target.value)}
                            input={
                                <OutlinedInput
                                    label="Destino"
                                    endAdornment={
                                        destino ? (
                                            <IconButton
                                                size="small"
                                                onClick={(e) => {
                                                    e.stopPropagation();
                                                    setDestino("");
                                                }}
                                                edge="end"
                                                sx={{ mr: 1 }}
                                            >
                                                <CloseIcon fontSize="small" />
                                            </IconButton>
                                        ) : null
                                    }
                                />
                            }
                        >
                            {aeroportos.map((aeroporto) => (
                                <MenuItem key={aeroporto.codigoIATA} value={aeroporto.codigoIATA}>
                                    {aeroporto.nome} ({aeroporto.codigoIATA})
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                </Box>


                <Button
                    variant="contained"
                    onClick={buscarVoos}
                    disabled={loading}
                    sx={{ borderRadius: theme.shape.borderRadius }}
                    fullWidth
                >
                    {loading ? <CircularProgress size={24} /> : 'Buscar Voos'}
                </Button>

                {error && (
                    <Alert severity="error" sx={{ mt: 2 }}>
                        {error}
                    </Alert>
                )}
            </Paper>

            {loading && voos.length === 0 ? (
                <Box display="flex" justifyContent="center" py={4}>
                    <CircularProgress />
                </Box>
            ) : voos.length > 0 ? (
                <Paper elevation={3} >
                    <TableContainer>
                        <Table>
                            <TableHead>
                                <TableRow>
                                    <TableCell>Código</TableCell>
                                    <TableCell>Origem</TableCell>
                                    <TableCell>Destino</TableCell>
                                    <TableCell>Data/Hora</TableCell>
                                    <TableCell>Preço (R$)</TableCell>
                                    <TableCell>Milhas Necessárias</TableCell>
                                    <TableCell>Ações</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {voos.map((voo) => (
                                    <TableRow key={voo.codigo}>
                                        <TableCell>{voo.codigo}</TableCell>
                                        <TableCell>{voo.aeroporto_origem.codigo}</TableCell>
                                        <TableCell>{voo.aeroporto_destino.codigo}</TableCell>
                                        <TableCell>
                                            {new Date(voo.data).toLocaleString('pt-BR')}
                                        </TableCell>
                                        <TableCell>{voo.valor_passagem?.toFixed(2)}</TableCell>
                                        <TableCell>{(voo.valor_passagem / 5).toLocaleString()}</TableCell>
                                        <TableCell>
                                            <Button
                                                variant="contained"
                                                color="primary"
                                                onClick={() => selecionarVoo(voo)}
                                                disabled={loading}
                                            >
                                                {loading ? <CircularProgress size={24} /> : 'Selecionar'}
                                            </Button>
                                        </TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                </Paper>
            ) : (
                <Paper elevation={3} sx={{ p: 3, textAlign: 'center' }}>
                    <Typography>Nenhum voo encontrado. Faça uma busca para ver os voos disponíveis.</Typography>
                </Paper>
            )}
        </Box>
    );
}