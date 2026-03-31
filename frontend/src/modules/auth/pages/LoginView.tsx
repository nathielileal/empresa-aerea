import { Box, Typography, Paper, Button } from '@mui/material';
import { LoginForm } from '../components/LoginForm';
import { useLoginViewModel } from '../view-models/useLoginViewModel';
import { useNavigate } from 'react-router-dom';

export function LoginView() {
  const { error, loading, handleLogin } = useLoginViewModel();
  const navigate = useNavigate();

  const handleBack = () => {
    navigate(-1);
  };

  return (
    <Box
      color={"Background"}
      width={"100vw"}
      height={"100vh"}
      display={"flex"}
      justifyContent={"center"}
      alignItems={"center"}
    >
      <Paper elevation={4}>
        <Typography
          variant="h3"
          align="center"
        >
          ✈️ Decole conosco
        </Typography>

        <LoginForm
          onSubmit={handleLogin}
          error={error}
          loading={loading}
        />

        <Button
          fullWidth
          variant="contained"
          onClick={handleBack}
        >
          Voltar
        </Button>
      </Paper>
    </Box>
  );
}