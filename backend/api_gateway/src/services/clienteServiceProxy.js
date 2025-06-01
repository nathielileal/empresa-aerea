const proxy = require('express-http-proxy');

module.exports = proxy('http://mscliente:8081');
