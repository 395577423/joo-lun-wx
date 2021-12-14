import request from '@/utils/request'

// 查询课程视频列表
export function getPage(query) {
  return request({
    url: '/course/video/page',
    method: 'get',
    params: query
  })
}

// 查询课程视频详细
export function getObj(id) {
  return request({
    url: '/course/video/' + id,
    method: 'get'
  })
}

// 新增课程视频
export function addObj(data) {
  return request({
    url: '/course/video',
    method: 'post',
    data: data
  })
}

// 修改课程视频
export function putObj(data) {
  return request({
    url: '/course/video',
    method: 'put',
    data: data
  })
}

// 删除课程视频
export function delObj(id) {
  return request({
    url: '/course/video/' + id,
    method: 'delete'
  })
}
