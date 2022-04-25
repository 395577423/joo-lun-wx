import request from '@/utils/request'

// 查询课程列表
export function getPage(query) {
  return request({
    url: '/empower/page',
    method: 'get',
    params: query
  })
}

// 查询课程详细
export function getObj(id) {
  return request({
    url: '/empower/' + id,
    method: 'get'
  })
}

// 新增课程
export function addObj(data) {
  return request({
    url: '/empower',
    method: 'post',
    data: data
  })
}

// 修改课程
export function putObj(data) {
  return request({
    url: '/empower',
    method: 'put',
    data: data
  })
}

// 删除课程
export function delObj(id) {
  return request({
    url: '/empower/' + id,
    method: 'delete'
  })
}
