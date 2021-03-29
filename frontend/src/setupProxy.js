const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
    app.use(
        '/',
        createProxyMiddleware({
            target: 'https://ds2020-horatiuspaniol-1.herokuapp.com',
            changeOrigin: true,
        })
    );
};