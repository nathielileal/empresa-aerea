const jwt = require('jsonwebtoken');

exports.login = (req, res) => {
    const { user, password } = req.body;

    // Validação mockada
    if (user === 'admin' && password === 'admin') {
        const id = 1;
        const token = jwt.sign({ id }, process.env.SECRET, { expiresIn: 300 });
        return res.json({ auth: true, token });
    }

    return res.status(401).json({ message: 'Login inválido!' });
};

exports.logout = (req, res) => {
    res.json({ auth: false, token: null });
};
