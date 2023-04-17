const { defineConfig } = require('@vue/cli-service')
// module.exports = defineConfig({
//   transpileDependencies: true,
//   devServer: {
//     porxy: {
//       '/api': {
//         target: 'http://localhost:8081',//代理地址，这里设置的地址会代替axios中设置的baseURL
//         changeOrigin: true,// 如果接口跨域，需要进行这个参数配置
//         //ws: true, // proxy websockets
//         //pathRewrite方法重写url
//         pathRewrite: {
//           '^/face': '/'
//           //pathRewrite: {'^/api': '/'} 重写之后url为 http://192.168.1.16:8085/xxxx
//           //pathRewrite: {'^/api': '/api'} 重写之后url为 http://192.168.1.16:8085/api/xxxx
//         }
//       }
//     },
//     allowedHosts: "all",
//   }
// })
module.exports = {
  lintOnSave: process.env.NODE_ENV !== 'production',
  publicPath: process.env.NODE_ENV === 'production'
    ? '/idphotomaker/'
    : '/',
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8081',//代理地址，这里设置的地址会代替axios中设置的baseURL
        changeOrigin: true,// 如果接口跨域，需要进行这个参数配置
        //ws: true, // proxy websockets
        //pathRewrite方法重写url
        pathRewrite: {
          '^/api': '/'
          //pathRewrite: {'^/api': '/'} 重写之后url为 http://192.168.1.16:8085/xxxx
          //pathRewrite: {'^/api': '/api'} 重写之后url为 http://192.168.1.16:8085/api/xxxx
        }
      }
    },
    allowedHosts: "all",
  }
}
