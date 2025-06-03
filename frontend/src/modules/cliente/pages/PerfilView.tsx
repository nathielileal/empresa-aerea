import { usePerfilViewModel } from '../view-models/usePerfilViewModel';
import { TabelaReservas } from '../components/TabelaReservas';
import { useNavigate } from 'react-router-dom';
import { Box, Card, CardContent, Typography, Button } from '@mui/material';
import RefreshIcon from '@mui/icons-material/Refresh';

function PerfilView() {
  const navigate = useNavigate();
  const { user, reservas, loading, error, saldo_milhas, cancelarReserva, recarregar } = usePerfilViewModel();
  const handleServiceClick = (serviceTitle: string) => {
    if (serviceTitle === "Reservar voos") {
      navigate("/cliente/reservar"); // BuscaVoosView
    } else if (serviceTitle === "Comprar milhas") {
      navigate("/cliente/comprarMilhas");

    }
    else if (serviceTitle === "Fazer check-in") {
      navigate("/cliente/checkin");
    }
  };


  const handleVerDetalhes = (reservaId: string) => {
    navigate(`/cliente/reservas/${reservaId}`);
  };

  if (loading && !user) return <div>Carregando...</div>;
  if (error) return <div>Erro: {error}</div>;
  if (!user) return <div>Usuário não encontrado</div>;

  return (
    <Box sx={{ display: "flex", height: "100vh", width: "100vw" }}>
      <Box sx={{ width: 450, paddingLeft: 1, display: "flex", flexDirection: "column", justifyContent: "start" }}>
        <Typography variant="h6" sx={{ mb: 2, fontWeight: "bold" }}>
          Serviços
        </Typography>
        {[
          { title: "Comprar milhas", desc: "Compre milhas para usar em suas reservas de voos.", icon: "💳" },
          { title: "Reservar voos", desc: "Pesquise e reserve voos com milhas ou dinheiro.", icon: "✈️" },
          { title: "Consultar reserva por código", desc: "Digite o código para ver os detalhes da reserva.", icon: "🔍" },
          { title: "Fazer check-in", desc: "Realize o check-in para os próximos voos.", icon: "✅" },
        ].map((service, index) => (
          <Card
            key={index}
            sx={{ mb: 2, display: "flex", alignItems: "start" }}
            onClick={() => handleServiceClick(service.title)}
          >
            <CardContent sx={{ p: 1 }}>
              <Typography variant="body2" sx={{ fontWeight: "bold" }}>
                {service.icon} {service.title}
              </Typography>
              <Typography variant="caption">
                {service.desc}
              </Typography>
            </CardContent>
          </Card>
        ))}
      </Box>

      <Box sx={{ display: "flex", flexDirection: "column", width: "100vw", paddingLeft: 4, paddingRight: 8 }}>
        <Box>
          <Typography variant="h6" sx={{ fontWeight: "bold" }}>
            Saldo em milhas
          </Typography>
          <Typography variant="h3" sx={{ color: "#374151", fontWeight: "bold" }}>
            {saldo_milhas.toLocaleString()}
          </Typography>
        </Box>

        <Box sx={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
          <Typography variant="h6">Voos e Reservas</Typography>
          <Button onClick={recarregar} variant="contained" color="primary" size="small" sx={{ width: 20, textTransform: "none" }} disabled={loading}>
            <RefreshIcon fontSize="small" />
          </Button>
        </Box>

        {error && <Typography color="error">{error}</Typography>}

        {loading && !reservas.length ? (
          <Typography>Carregando reservas...</Typography>
        ) : (
          <Box>
            <TabelaReservas reservas={reservas} onVerDetalhes={handleVerDetalhes} onCancelar={cancelarReserva} />
          </Box>
        )}
      </Box>
    </Box>
  );
}

export default PerfilView;
