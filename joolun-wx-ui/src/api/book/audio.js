import request from '@/utils/request'

// 查询用户书籍录音列表
export function listAudio(query) {
  return request({
    url: '/system/audio/list',
    method: 'get',
    params: query
  })
}

// 查询用户书籍录音详细
export function getAudio(id) {
  return request({
    url: '/system/audio/' + id,
    method: 'get'
  })
}

// 新增用户书籍录音
export function addAudio(data) {
  return request({
    url: '/system/audio',
    method: 'post',
    data: data
  })
}

// 修改用户书籍录音
export function updateAudio(data) {
  return request({
    url: '/system/audio',
    method: 'put',
    data: data
  })
}

// 删除用户书籍录音
export function delAudio(id) {
  return request({
    url: '/system/audio/' + id,
    method: 'delete'
  })
}

// 导出用户书籍录音
export function exportAudio(query) {
  return request({
    url: '/system/audio/export',
    method: 'get',
    params: query
  })
}