const proxy = require('express-http-proxy');

module.exports = proxy('http://mssaga:8082');
