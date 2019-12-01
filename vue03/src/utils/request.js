/* eslint-disable no-console */
import axios from "axios";

import { MessageBox, Message } from "element-ui";

// create an axios instance
const service = axios.create({
  baseURL: `http://127.0.0.1:8080/book`, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 5000 // request timeout
});

console.log(service);
// request 接口
service.interceptors.request.use(
  config => {
    // do something before request is sent

    return config;
  },
  error => {
    // do something with request error
    console.log(error); // for debug
    return Promise.reject(error);
  }
);

// response 接口
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
   */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    const res = response.data;

    // if the custom code is not 20000, it is judged as an error.
    if (res.code !== 20000) {
      Message({
        message: res.message || "请求错误",
        type: "error",
        duration: 5 * 1000
      });

      // 50008: Illegal token; 50012: Other clients logged in; 50014: Token expired;
      if (res.code === 50008 || res.code === 50012 || res.code === 50014) {
        // to re-login
        MessageBox("您已经注销或登录超时，请重新登录", "退出", {
          confirmButtonText: "Re-Login",
          cancelButtonText: "Cancel",
          type: "warning"
        }).then(() => {});
      }
      return Promise.reject(new Error(res.message || "请求错误"));
    } else {
      return res;
    }
  },
  error => {
    console.log("err" + error); // for debug
    Message({
      message: error.message,
      type: "error",
      duration: 5 * 1000
    });
    return Promise.reject(error);
  }
);

export default service;
