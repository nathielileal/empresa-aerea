const express = require('express');
const router = express.Router();
const verifyJWT = require('../middlewares/auth');
const sagaServiceProxy = require('../services/sagaServiceProxy');
const reservaServiceProxy = require('../services/reservaServiceProxy');

router.get('/reservas', verifyJWT, reservaServiceProxy);
router.get('/reservas/:id', verifyJWT, reservaServiceProxy);
router.delete('/reservas/:id', verifyJWT, sagaServiceProxy);
router.get('/clientes/:id/reservas', verifyJWT, reservaServiceProxy);
router.get('/reservas/:id/milhas', verifyJWT, reservaServiceProxy);
router.put('/reservas/:id/milhas', verifyJWT, reservaServiceProxy);
router.post('/reservas', sagaServiceProxy); 


module.exports = router;''
