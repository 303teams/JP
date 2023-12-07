module.exports={
    css: {
        loaderOptions: {
            postcss: {
                postcssOptions:{
                    plugins: [
                        require('postcss-pxtorem')({ // 把px单位换算成rem单位
                            rootValue: 145, //换算基数，
                            unitPrecision: 3, //允许REM单位增长到的十进制数字,小数点后保留的位数。
                            propList: ['*'],
                            selectorBlackList: [],
                            replace: true,
                            mediaQuery: false,  //（布尔值）允许在媒体查询中转换px。
                            minPixelValue: 1 //设置要替换的最小像素值
                        })
                    ]
                }
            }
        }
    }
}
