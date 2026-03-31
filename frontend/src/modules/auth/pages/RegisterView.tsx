import { Box, Typography, Paper, Button, Alert } from '@mui/material';
import { RegisterForm } from '../components/RegisterForm';
import { useRegisterViewModel } from '../view-models/useRegisterViewModel';
import { useNavigate } from 'react-router-dom';

export function RegisterView() {
  const { error, loading, success, successMessage, handleRegister } = useRegisterViewModel();
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
      <Paper elevation={4} sx={{ p: 3, width: '100%', maxWidth: 400 }}>
        <Typography
          variant="h3"
          align="center"
          sx={{ mb: 2 }}
        >
          ✈️ Decole conosco
        </Typography>

        {success ? (
          <Alert severity="success" sx={{ mb: 2 }}>
            {successMessage}
          </Alert>
        ) : (
          <>
            <RegisterForm
              onSubmit={handleRegister}
              error={error}
              loading={loading}
            />

            <Button
              fullWidth
              variant="contained"
              onClick={handleBack}
              sx={{ mt: 2 }}
            >
              Voltar
            </Button>
          </>
        )}
      </Paper>
    </Box>
  );
}