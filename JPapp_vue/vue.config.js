module.exports = {
    publicPath: "./",
    outputDir: "dist",
    lintOnSave: true,
    css: {
        loaderOptions: {
            css: {},
            postcss: {
                plugins: [
                    require("postcss-px2rem")({
                        remUnit: 192///设计图宽度/10
                    })
                ]
            }
        }
    }
};

