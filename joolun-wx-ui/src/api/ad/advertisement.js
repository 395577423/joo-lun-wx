import request from '@/utils/request'

// 查询广告列表
export function listObj(query) {
  return request({
    url: '/advertisement/list',
    method: 'get',
    params: query
  })
}

// 查询广告详细
export function getObj(id) {
  return request({
    url: '/advertisement/' + id,
    method: 'get'
  })
}

// 新增广告
export function addObj(data) {
  return request({
    url: '/advertisement',
    method: 'post',
    data: data
  })
}

// 修改广告
export function putObj(data) {
  return request({
    url: '/advertisement',
    method: 'put',
    data: data
  })
}

// 删除广告
export function delObj(id) {
  return request({
    url: '/advertisement/' + id,
    method: 'delete'
  })
}

// 导出广告
export function exportObj(query) {
  return request({
    url: '/advertisement/export',
    method: 'get',
    params: query
  })
}
