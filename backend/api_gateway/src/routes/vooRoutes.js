const express = require('express');
const router = express.Router();
const verifyJWT = require('../middlewares/auth');
const sagaServiceProxy = require('../services/sagaServiceProxy');
const voosServiceProxy = require('../services/voosServiceProxy');

router.get('/voos', verifyJWT, voosServiceProxy);
router.get('/voos/:codigo', verifyJWT, voosServiceProxy);
router.get('/voos/aeroportos', verifyJWT, voosServiceProxy);
router.post('/voos', verifyJWT, voosServiceProxy);
router.patch('/voos/:codigo/estado', verifyJWT, voosServiceProxy);

module.exports = router;
