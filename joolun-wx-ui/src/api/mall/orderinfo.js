import request from '@/utils/request'

export function getPage(query) {
  return request({
    url: '/orderinfo/page',
    method: 'get',
    params: query
  })
}

export function getCount(query) {
  return request({
    url: '/orderinfo/count',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/orderinfo',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/orderinfo/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/orderinfo/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/orderinfo',
    method: 'put',
    data: obj
  })
}

export function editPrice(obj) {
  return request({
    url: '/orderinfo/editPrice',
    method: 'put',
    data: obj
  })
}

export function orderCancel(id) {
  return request({
    url: '/orderinfo/cancel/' + id,
    method: 'put'
  })
}

export function takeGoods(id) {
  return request({
    url: '/orderinfo/takegoods/' + id,
    method: 'put'
  })
}
