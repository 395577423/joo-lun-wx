import request from '@/utils/request'

// 查询书籍列表
export function getPage(query) {
  return request({
    url: '/course/book/page',
    method: 'get',
    params: query
  })
}

// 查询书籍详细
export function getObj(id) {
  return request({
    url: '/course/book/' + id,
    method: 'get'
  })
}

// 新增书籍
export function addObj(data) {
  return request({
    url: '/course/book',
    method: 'post',
    data: data
  })
}

// 修改书籍
export function putObj(data) {
  return request({
    url: '/course/book',
    method: 'put',
    data: data
  })
}

// 删除书籍
export function delObj(id) {
  return request({
    url: '/course/book/' + id,
    method: 'delete'
  })
}

// 导出书籍
export function exportBook(query) {
  return request({
    url: '/course/book/export',
    method: 'get',
    params: query
  })
}
