import request from '@/utils/request'

// 查询书籍列表
export function listBook(query) {
  return request({
    url: '/book/page',
    method: 'get',
    params: query
  })
}

// 查询书籍详细
export function getBook(id) {
  return request({
    url: '/book/' + id,
    method: 'get'
  })
}

// 新增书籍
export function addBook(data) {
  return request({
    url: '/book',
    method: 'post',
    data: data
  })
}

// 修改书籍
export function updateBook(data) {
  return request({
    url: '/book',
    method: 'put',
    data: data
  })
}

// 删除书籍
export function delBook(id) {
  return request({
    url: '/book/' + id,
    method: 'delete'
  })
}

// 导出书籍
export function exportBook(query) {
  return request({
    url: '/book/export',
    method: 'get',
    params: query
  })
}