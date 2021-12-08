import request from '@/utils/request'

// 查询用户答案列表
export function listAnswer(query) {
  return request({
    url: '/system/answer/list',
    method: 'get',
    params: query
  })
}

// 查询用户答案详细
export function getAnswer(id) {
  return request({
    url: '/system/answer/' + id,
    method: 'get'
  })
}

// 新增用户答案
export function addAnswer(data) {
  return request({
    url: '/system/answer',
    method: 'post',
    data: data
  })
}

// 修改用户答案
export function updateAnswer(data) {
  return request({
    url: '/system/answer',
    method: 'put',
    data: data
  })
}

// 删除用户答案
export function delAnswer(id) {
  return request({
    url: '/system/answer/' + id,
    method: 'delete'
  })
}

// 导出用户答案
export function exportAnswer(query) {
  return request({
    url: '/system/answer/export',
    method: 'get',
    params: query
  })
}