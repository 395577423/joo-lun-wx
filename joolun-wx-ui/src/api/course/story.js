import request from '@/utils/request'

// 查询书籍故事列表
export function getPage(query) {
  return request({
    url: '/course/story/page',
    method: 'get',
    params: query
  })
}

// 查询书籍故事详细
export function getObj(id) {
  return request({
    url: '/course/story/' + id,
    method: 'get'
  })
}

// 新增书籍故事
export function addObj(data) {
  return request({
    url: '/course/story',
    method: 'post',
    data: data
  })
}

// 修改书籍故事
export function putObj(data) {
  return request({
    url: '/course/story',
    method: 'put',
    data: data
  })
}

// 删除书籍故事
export function delObj(id) {
  return request({
    url: '/course/story/' + id,
    method: 'delete'
  })
}

