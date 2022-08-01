import request from '@/utils/request'

// 查询用户答案列表
export function listLibrary(query) {
  return request({
    url: '/library/page',
    method: 'get',
    params: query
  })
}

// 查询用户答案详细
export function getLibrary(id) {
  return request({
    url: '/library/' + id,
    method: 'get'
  })
}

// 新增用户答案
export function addLibrary(data) {
  return request({
    url: '/library',
    method: 'post',
    data: data
  })
}

// 修改用户答案
export function updateLibrary(data) {
  return request({
    url: '/library',
    method: 'put',
    data: data
  })
}

// 删除用户答案
export function delLibrary(id) {
  return request({
    url: '/library/' + id,
    method: 'delete'
  })
}

// 导出用户答案
export function exportLibrary(query) {
  return request({
    url: '/library/export',
    method: 'get',
    params: query
  })
}