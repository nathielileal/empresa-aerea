const axios = require('axios');

module.exports = async (req, res) => {
  try {
    const response = await axios({
      method: req.method,
      url: `http://msvoo:9091${req.originalUrl}`,
      headers: req.headers,
      data: req.body,
      params: req.params,
    });

    res.status(response.status).json(response.data);
  } catch (error) {
    console.error('Erro no proxy para msvoo:', error.message);
    if (error.response) {
      res.status(error.response.status).json(error.response.data);
    } else {
      res.status(502).json({ erro: 'Serviço msvoo indisponível' });
    }
  }
};
