const express = require('express');
const router = express.Router();

const clienteRoutes = require('./clienteRoutes');
const funcionarioRoutes = require('./funcionarioRoutes');
const reservaRoutes = require('./reservaRoutes');
const authRoutes = require('./authRoutes');
// const userRoutes = require('./userRoutes');



// Serviços protegidos
// router.use('/', userRoutes);
router.use('/', authRoutes);
router.use('/', clienteRoutes);
router.use('/', funcionarioRoutes);
router.use('/', reservaRoutes);

// aqui vai outras rotas
// router.use('/reservas', require('./reservasRoutes'));

module.exports = router;
