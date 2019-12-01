// eslint-disable-next-line no-unused-vars
const path = require("path");

function resolve(dir) {
  return path.join(__dirname, dir);
}

module.exports = {
  devServer: {
    port: 8090,
    open: true,
    overlay: {
      warnings: false,
      errors: true
    },
    proxy: {
      // change xxx-api/login => mock/login
      // detail: https://cli.vuejs.org/config/#devserver-proxy
      [process.env.VUE_APP_BASE_API]: {
        target: `http://127.0.0.1:8080/book`,
        changeOrigin: true,
        pathRewrite: {
          ["^" + process.env.VUE_APP_BASE_API]: ""
        }
      }
    }
  },
  configureWebpack: {
    resolve: {
      // 自动补全的扩展名
      extensions: [".js", ".vue", ".json"],
      // 默认路径代理
      // 例如 import Vue from 'vue'，会自动到 'vue/dist/vue.common.js'中寻找
      alias: {
        "@": resolve("src"),
        "@config": resolve("config")
      }
    }
  }
};
