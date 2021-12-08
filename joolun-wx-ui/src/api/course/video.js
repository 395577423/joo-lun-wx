import request from '@/utils/request'

// 查询课程视频列表
export function listVideo(query) {
  return request({
    url: '/course/video/page',
    method: 'get',
    params: query
  })
}

// 查询课程视频详细
export function getVideo(id) {
  return request({
    url: '/course/video/' + id,
    method: 'get'
  })
}

// 新增课程视频
export function addVideo(data) {
  return request({
    url: '/course/video',
    method: 'post',
    data: data
  })
}

// 修改课程视频
export function updateVideo(data) {
  return request({
    url: '/course/video',
    method: 'put',
    data: data
  })
}

// 删除课程视频
export function delVideo(id) {
  return request({
    url: '/course/video/' + id,
    method: 'delete'
  })
}

// 导出课程视频
export function exportVideo(query) {
  return request({
    url: '/course/video/export',
    method: 'get',
    params: query
  })
}