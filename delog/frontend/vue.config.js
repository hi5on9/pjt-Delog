module.exports = {
    runtimeCompiler: true,
    transpileDependencies: ['vuetify'],
    publicPath: '/',
    filenameHashing: true,
    devServer: {
        disableHostCheck: true,
        port: 80,
        public: '0.0.0.0:80',
    },
};
