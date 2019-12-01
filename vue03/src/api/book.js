/* eslint-disable no-console */
import request from "@/utils/request";

export function getBookList(params) {
  return request({
    url: "/book/list",
    method: "get",
    params: params
  });
}

export function deleteBook(params) {
  return request({
    url: "/book/" + params,
    method: "delete"
  });
}

export function addBook(params) {
  return request({
    url: "/book",
    method: "post",
    params: params
  });
}

export function updateBook(params) {
  console.log(params);
  return request({
    url: "/book",
    method: "put",
    params: params
  });
}
