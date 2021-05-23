module.exports = {
    devServer: {
        port: 8090, //dev server port
        proxy: {
            '/api': {
                //url에 /api패턴이 있을경우 spring rest api 8080포트를 바로보게 한다.
                target: 'http://localhost:8080/',
                changeOrigin: true
            }
        }
    },
    transpileDependencies: ['vuetify']
}
