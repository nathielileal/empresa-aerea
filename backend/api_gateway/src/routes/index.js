const express = require('express');
const router = express.Router();

const clienteRoutes = require('./clienteRoutes');
const authRoutes = require('./authRoutes');
const userRoutes = require('./userRoutes');



// Serviços protegidos
router.use('/', userRoutes);
router.use('/', authRoutes);
router.use('/', clienteRoutes);

// aqui vai outras rotas
// router.use('/reservas', require('./reservasRoutes'));

module.exports = router;
