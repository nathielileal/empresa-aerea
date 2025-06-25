const express = require('express');
const router = express.Router();
const verifyJWT = require('../middlewares/auth');
const sagaServiceProxy = require('../services/sagaServiceProxy');
const funcionarioServiceProxy = require('../services/funcionarioServiceProxy');

router.get('/funcionarios', verifyJWT, funcionarioServiceProxy);
router.get('/funcionarios/:id', verifyJWT, funcionarioServiceProxy);
router.get('/funcionarios/:id/milhas', verifyJWT, funcionarioServiceProxy);
router.put('/funcionarios/:id/milhas', verifyJWT, funcionarioServiceProxy);
router.delete('/funcionarios/:id', verifyJWT, funcionarioServiceProxy);
router.put('/funcionarios/:id', verifyJWT, funcionarioServiceProxy);
router.post('/funcionarios', funcionarioServiceProxy); //cadastrosaga

module.exports = router;    
