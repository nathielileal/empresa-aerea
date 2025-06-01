const express = require('express');
const router = express.Router();
const verifyJWT = require('../middlewares/auth');
const authServiceProxy = require('../services/authServiceProxy');

router.get('/auth/usuarios', verifyJWT, authServiceProxy);
router.post('/auth/login', authServiceProxy);



module.exports = router;
