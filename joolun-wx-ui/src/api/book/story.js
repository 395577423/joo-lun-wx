import request from '@/utils/request'

// 查询书籍故事列表
export function listStory(query) {
  return request({
    url: '/book/story/page',
    method: 'get',
    params: query
  })
}

// 查询书籍故事详细
export function getStory(id) {
  return request({
    url: '/book/story/' + id,
    method: 'get'
  })
}

// 新增书籍故事
export function addStory(data) {
  return request({
    url: '/book/story',
    method: 'post',
    data: data
  })
}

// 修改书籍故事
export function updateStory(data) {
  return request({
    url: '/book/story',
    method: 'put',
    data: data
  })
}

// 删除书籍故事
export function delStory(id) {
  return request({
    url: '/book/story/' + id,
    method: 'delete'
  })
}

// 导出书籍故事
export function exportStory(query) {
  return request({
    url: '/book/story/export',
    method: 'get',
    params: query
  })
}