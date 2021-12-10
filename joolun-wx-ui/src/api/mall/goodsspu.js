import request from '@/utils/request'

export function getPage(query) {
  return request({
    url: '/goodsspu/page',
    method: 'get',
    params: query
  })
}

export function getCount(query) {
  return request({
    url: '/goodsspu/count',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/goodsspu',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/goodsspu/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/goodsspu/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/goodsspu',
    method: 'put',
    data: obj
  })
}

export function putObjShelf(obj) {
  return request({
    url: '/goodsspu/shelf',
    method: 'put',
    params: obj
  })
}
