const express = require('express');
const router = express.Router();
const verifyJWT = require('../middlewares/auth');
const sagaServiceProxy = require('../services/sagaServiceProxy');
const clienteServiceProxy = require('../services/clienteServiceProxy');

router.get('/clientes', verifyJWT, clienteServiceProxy);
router.get('/clientes/:id', verifyJWT, clienteServiceProxy);
router.get('/clientes/:id/transacoes', verifyJWT, clienteServiceProxy);
router.put('/clientes/:id/milhas', verifyJWT, clienteServiceProxy);
router.post('/clientes', sagaServiceProxy); //autocadastro


module.exports = router;
