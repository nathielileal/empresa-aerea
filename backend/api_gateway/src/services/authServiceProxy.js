const proxy = require('express-http-proxy');

module.exports = proxy('http://auth-service:8083', {
  proxyReqPathResolver: (req) => {
    return `${req.url}`; 
  },
  userResDecorator: async (proxyRes, proxyResData, req, res) => {
    // Retorna a resposta do backend diretamente para o frontend
    const contentType = proxyRes.headers['content-type'] || '';

    if (contentType.includes('application/json')) {
      return JSON.parse(proxyResData.toString('utf8'));
    }

    return proxyResData;
  },
  preserveHostHdr: true
});
