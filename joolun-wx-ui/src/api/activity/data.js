import request from '@/utils/request'

// 查询社会活动列表
export function listActivity(query) {
  return request({
    url: '/activity/list',
    method: 'get',
    params: query
  })
}

// 查询社会活动详细
export function getActivity(id) {
  return request({
    url: '/activity/' + id,
    method: 'get'
  })
}

// 新增社会活动
export function addActivity(data) {
  return request({
    url: '/activity',
    method: 'post',
    data: data
  })
}

// 修改社会活动
export function updateActivity(data) {
  return request({
    url: '/activity',
    method: 'put',
    data: data
  })
}

// 删除社会活动
export function delActivity(id) {
  return request({
    url: '/activity/' + id,
    method: 'delete'
  })
}

// 导出社会活动
export function exportActivity(query) {
  return request({
    url: '/activity/export',
    method: 'get',
    params: query
  })
}

export function relateCourse(data) {
  return request({
    url: '/activity/relate/course',
    method: 'post',
    data: data
  })
}
