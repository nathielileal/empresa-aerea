const express = require('express');
const router = express.Router();
const verifyJWT = require('../middlewares/auth');
const sagaServiceProxy = require('../services/sagaServiceProxy');
const voosServiceProxy = require('../services/voosServiceProxy');

router.get('/voos', verifyJWT, voosServiceProxy);
router.get('/voos/:codigo', verifyJWT, voosServiceProxy);
router.get('/voos/aeroportos', verifyJWT, voosServiceProxy);
router.post('/voos', verifyJWT, voosServiceProxy);
router.put('/voos/:codigo/cancelar', verifyJWT, voosServiceProxy);
router.put('/voos/:codigo/realizar', verifyJWT, voosServiceProxy);

module.exports = router;
