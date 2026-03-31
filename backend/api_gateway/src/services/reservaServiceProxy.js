const axios = require('axios');

module.exports = async (req, res) => {
  try {
    const response = await axios({
      method: req.method,
      url: `http://msreserva:8084${req.originalUrl}`,
      headers: req.headers,
      data: req.body,
    });

    res.status(response.status).json(response.data);
  } catch (error) {
    console.error('Erro no proxy para msreserva:', error.message);
    if (error.response) {
      res.status(error.response.status).json(error.response.data);
    } else {
      res.status(502).json({ erro: 'Serviço msreserva indisponível' });
    }
  }
};
