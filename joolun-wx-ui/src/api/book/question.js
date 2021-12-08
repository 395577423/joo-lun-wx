import request from '@/utils/request'

// 查询书籍问题列表
export function listQuestion(query) {
  return request({
    url: '/system/question/list',
    method: 'get',
    params: query
  })
}

// 查询书籍问题详细
export function getQuestion(id) {
  return request({
    url: '/system/question/' + id,
    method: 'get'
  })
}

// 新增书籍问题
export function addQuestion(data) {
  return request({
    url: '/system/question',
    method: 'post',
    data: data
  })
}

// 修改书籍问题
export function updateQuestion(data) {
  return request({
    url: '/system/question',
    method: 'put',
    data: data
  })
}

// 删除书籍问题
export function delQuestion(id) {
  return request({
    url: '/system/question/' + id,
    method: 'delete'
  })
}

// 导出书籍问题
export function exportQuestion(query) {
  return request({
    url: '/system/question/export',
    method: 'get',
    params: query
  })
}