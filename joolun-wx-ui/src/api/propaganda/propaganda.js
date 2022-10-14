import request from '@/utils/request'

// 查询宣传视频列表
export function listObj(query) {
  return request({
    url: '/propaganda/list',
    method: 'get',
    params: query
  })
}

// 查询宣传视频详细
export function getObj(id) {
  return request({
    url: '/propaganda/' + id,
    method: 'get'
  })
}

// 新增宣传视频
export function addObj(data) {
  return request({
    url: '/propaganda',
    method: 'post',
    data: data
  })
}

// 修改宣传视频
export function putObj(data) {
  return request({
    url: '/propaganda',
    method: 'put',
    data: data
  })
}

// 删除宣传视频
export function delObj(id) {
  return request({
    url: '/propaganda/' + id,
    method: 'delete'
  })
}

// 导出宣传视频
export function exportObj(query) {
  return request({
    url: '/propaganda/export',
    method: 'get',
    params: query
  })
}
