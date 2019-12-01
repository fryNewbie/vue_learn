import Vue from "vue";
import App from "./App.vue";
// import "./registerServiceWorker";
import router from "./router";
import store from "./store";

// 导入bootstrap
// import "bootstrap";
// import "bootstrap/dist/css/bootstrap.css";

//axios导入,挂在到原型链
import axios from "axios";

import "./plugins/element.js";

Vue.prototype.$axios = axios;

//moment
import moment from "moment"; //导入文件

Vue.filter("dateformat", function(dataStr, pattern = "YYYY-MM-DD HH:mm:ss") {
  return moment(dataStr).format(pattern);
});

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
