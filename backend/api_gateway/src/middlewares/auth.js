const jwt = require('jsonwebtoken');

function verifyJWT(req, res, next) {
    const authHeader = req.headers['authorization'];
    if (!authHeader) {
        return res.status(401).json({ auth: false, message: 'Token não fornecido.' });
    }

    const parts = authHeader.split(' ');
    if (parts.length !== 2 || parts[0] !== 'Bearer') {
        return res.status(401).json({ auth: false, message: 'Formato do token inválido.' });
    }

    const token = parts[1]; 

    jwt.verify(token, process.env.SECRET, (err, decoded) => {
        if (err) return res.status(401).json({ auth: false, message: 'Token incorreto ou expirado.' });

        req.userId = decoded.id;
        next();
    });
}

module.exports = verifyJWT;
