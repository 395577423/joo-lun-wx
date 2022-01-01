import request from '@/utils/request'

export function getPage(query) {
  return request({
    url: '/bookcategory/page',
    method: 'get',
    params: query
  })
}

export function fetchTree(query) {
  return request({
    url: '/bookcategory/tree',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/bookcategory',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/bookcategory/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/bookcategory/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/bookcategory',
    method: 'put',
    data: obj
  })
}
