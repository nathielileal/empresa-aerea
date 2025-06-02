const express = require('express');
const router = express.Router();
const verifyJWT = require('../middlewares/auth');
const authServiceProxy = require('../services/authServiceProxy');

router.get('/usuarios', authServiceProxy);
router.post('/login', authServiceProxy);



module.exports = router;
