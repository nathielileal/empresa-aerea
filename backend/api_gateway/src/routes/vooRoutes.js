const express = require('express');
const router = express.Router();
const verifyJWT = require('../middlewares/auth');
const sagaServiceProxy = require('../services/sagaServiceProxy');
const voosServiceProxy = require('../services/voosServiceProxy');

router.get('/voos', verifyJWT, voosServiceProxy);
router.get('/voos/:id', verifyJWT, voosServiceProxy);
router.put('/voos/:id/cancelar', verifyJWT, voosServiceProxy);
router.put('/voos/:id/realizar', verifyJWT, voosServiceProxy);
router.post('/voos', verifyJWT,voosServiceProxy); 


module.exports = router;
