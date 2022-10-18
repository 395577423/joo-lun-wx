import request from '@/utils/request'

// 查询用户书籍录音列表
export function getPage(query) {
  return request({
    url: '/course/guide/page',
    method: 'get',
    params: query
  })
}

// 查询用户书籍录音详细
export function getObj(id) {
  return request({
    url: '/course/guide/' + id,
    method: 'get'
  })
}

// 新增用户书籍录音
export function addObj(data) {
  return request({
    url: '/course/guide',
    method: 'post',
    data: data
  })
}

// 修改用户书籍录音
export function putObj(data) {
  return request({
    url: '/course/guide',
    method: 'put',
    data: data
  })
}

// 删除用户书籍录音
export function delObj(id) {
  return request({
    url: '/course/guide/' + id,
    method: 'delete'
  })
}
