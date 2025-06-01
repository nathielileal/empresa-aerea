const express = require('express');
const router = express.Router();
const verifyJWT = require('../middlewares/auth');
const sagaServiceProxy = require('../services/sagaServiceProxy');
const clienteServiceProxy = require('../services/clienteServiceProxy');

router.get('/clientes', clienteServiceProxy);
router.post('/clientes', sagaServiceProxy);


module.exports = router;
