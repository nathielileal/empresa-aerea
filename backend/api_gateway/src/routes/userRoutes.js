const express = require('express');
const router = express.Router();
const verifyJWT = require('../middlewares/auth');
const userServiceProxy = require('../services/userServiceProxy');

// router.get('/usuarios', verifyJWT, userServiceProxy);

module.exports = router;
