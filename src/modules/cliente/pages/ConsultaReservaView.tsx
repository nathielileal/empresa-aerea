import React from 'react';
import {
    Box,
    TextField,
    Button,
    Card,
    CardContent,
    Typography,
    Grid,
    Divider,
    Chip
} from '@mui/material';
import { styled } from '@mui/material/styles';
import {
    FlightTakeoff,
    FlightLand,
    AttachMoney,
    AirlineSeatReclineNormal,
    Cancel
} from '@mui/icons-material';

// Componentes estilizados
const PageContainer = styled(Box)(({ theme }) => ({
    maxWidth: 800,
    margin: '0 auto',
    padding: theme.spacing(3),
    backgroundColor: theme.palette.background.default
}));

const PageTitle = styled(Typography)(({ theme }) => ({
    fontWeight: 'bold',
    marginBottom: theme.spacing(4),
    color: theme.palette.text.primary
}));

const SearchCard = styled(Card)(({ theme }) => ({
    padding: theme.spacing(3),
    borderRadius: theme.shape.borderRadius,
    boxShadow: theme.shadows[2],
    backgroundColor: theme.palette.background.paper
}));

const ReservationCard = styled(Card)(({ theme }) => ({
    marginTop: theme.spacing(3),
    borderRadius: theme.shape.borderRadius * 2,
    boxShadow: theme.shadows[3],
    backgroundColor: theme.palette.background.paper
}));

const SectionTitle = styled(Typography)(({ theme }) => ({
    margin: theme.spacing(3, 0, 2),
    color: theme.palette.primary.main,
    fontWeight: 'bold',
    textAlign: 'center'
}));

const InfoLabel = styled(Typography)(({ theme }) => ({
    color: theme.palette.text.secondary,
    marginBottom: theme.spacing(1),
    display: 'flex',
    alignItems: 'center',
    gap: theme.spacing(1)
}));

const InfoValue = styled(Typography)(({ theme }) => ({
    color: theme.palette.text.primary,
    marginBottom: theme.spacing(2)
}));

const ActionButton = styled(Button)(({ theme }) => ({
    height: 48,
    minWidth: 180,
    fontWeight: 'bold',
    textTransform: 'none',
    margin: theme.spacing(1)
}));

const ReservationConsulta = () => {
    return (
        <PageContainer>
            <PageTitle variant="h4" component="h1">
                Consultar Reserva
            </PageTitle>

            <SearchCard>
                <Box sx={{ display: 'flex', gap: 2, alignItems: 'flex-end' }}>
                    <TextField
                        label="Código da Reserva"
                        variant="outlined"
                        fullWidth
                        placeholder="Ex: ABC123"
                        InputLabelProps={{ shrink: true }}
                    />
                    <ActionButton variant="contained">
                        Buscar Reserva
                    </ActionButton>
                </Box>
            </SearchCard>

            <ReservationCard>
                <CardContent>
                    <SectionTitle variant="h5">
                        Dados da Reserva
                        <Chip
                            label="CONFIRMADO"
                            color="success"
                            sx={{ ml: 2, textTransform: 'uppercase' }}
                        />
                    </SectionTitle>

                    <Grid container spacing={3}>
                        <Grid item xs={12} md={6}>
                            <Box>
                                <InfoLabel variant="subtitle2">
                                    Código da Reserva
                                </InfoLabel>
                                <InfoValue variant="body1">ABC123</InfoValue>
                            </Box>

                            <Box>
                                <InfoLabel variant="subtitle2">
                                    Data/Hora
                                </InfoLabel>
                                <InfoValue variant="body1">15/10/2023 14:30</InfoValue>
                            </Box>
                        </Grid>

                        <Grid item xs={12} md={6}>
                            <Box>
                                <InfoLabel variant="subtitle2">
                                    <AttachMoney fontSize="small" /> Valor Gasto
                                </InfoLabel>
                                <InfoValue variant="body1">R$ 450,90</InfoValue>
                            </Box>

                            <Box>
                                <InfoLabel variant="subtitle2">
                                    <AirlineSeatReclineNormal fontSize="small" /> Milhas Gastas
                                </InfoLabel>
                                <InfoValue variant="body1">2.000</InfoValue>
                            </Box>
                        </Grid>
                    </Grid>

                    <Divider sx={{ my: 2 }} />

                    <SectionTitle variant="h5">Rota do Voo</SectionTitle>

                    <Grid container spacing={2} alignItems="center" justifyContent="center">
                        <Grid item xs={5}>
                            <Box>
                                <InfoLabel variant="subtitle2">
                                    <FlightTakeoff color="primary" fontSize="small" /> Origem
                                </InfoLabel>
                                <InfoValue variant="body1">São Paulo (GRU)</InfoValue>
                            </Box>
                        </Grid>

                        <Grid item xs={2} sx={{ textAlign: 'center' }}>
                            <FlightLand sx={{ transform: 'rotate(90deg)', color: 'text.secondary' }} />
                        </Grid>

                        <Grid item xs={5}>
                            <Box>
                                <InfoLabel variant="subtitle2">
                                    <FlightLand color="primary" fontSize="small" /> Destino
                                </InfoLabel>
                                <InfoValue variant="body1">Rio de Janeiro (GIG)</InfoValue>
                            </Box>
                        </Grid>
                    </Grid>

                    <Divider sx={{ my: 2 }} />

                    <SectionTitle variant="h5">Ações</SectionTitle>

                    <Box sx={{ display: 'flex', gap: 2, justifyContent: 'center', mt: 3 }}>
                        <ActionButton
                            variant="contained"
                            color="primary"
                            startIcon={<AirlineSeatReclineNormal />}
                        >
                            Fazer Check-in
                        </ActionButton>

                        <ActionButton
                            variant="outlined"
                            color="error"
                            startIcon={<Cancel />}
                        >
                            Cancelar Reserva
                        </ActionButton>
                    </Box>
                </CardContent>
            </ReservationCard>
        </PageContainer>
    );
};

export default ReservationConsulta;