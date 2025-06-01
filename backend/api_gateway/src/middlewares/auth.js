const jwt = require('jsonwebtoken');

function verifyJWT(req, res, next) {
    const token = req.headers['authorization'];
    if (!token) return res.status(401).json({ auth: false, message: 'Token não fornecido.' });

    jwt.verify(token, process.env.SECRET, (err, decoded) => {
        if (err) return res.status(500).json({ auth: false, message: 'Falha ao autenticar o token.' });

        req.userId = decoded.id;
        next();
    });
}

module.exports = verifyJWT;
