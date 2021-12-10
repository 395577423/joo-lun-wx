import request from '@/utils/request'

// 查询书籍问题列表
export function getPage(query) {
  return request({
    url: '/course/question/page',
    method: 'get',
    params: query
  })
}

// 查询书籍问题详细
export function getQuestion(id) {
  return request({
    url: '/course/question/' + id,
    method: 'get'
  })
}

// 新增书籍问题
export function addObj(data) {
  return request({
    url: '/course/question',
    method: 'post',
    data: data
  })
}

// 修改书籍问题
export function putObj(data) {
  return request({
    url: '/course/question',
    method: 'put',
    data: data
  })
}

// 删除书籍问题
export function delObj(id) {
  return request({
    url: '/course/question/' + id,
    method: 'delete'
  })
}

