import request from '@/utils/request'

// 查询书籍问题列表
export function getPage(query) {
  return request({
    url: '/course/question/page',
    method: 'get',
    params: query
  })
}
// 查询书籍问题列表
export function getPage2(query) {
  return request({
    url: '/course/question/page2',
    method: 'get',
    params: query
  })
}

// 查询书籍问题列表
export function questionDetail(id) {
  return request({
    url: '/course/question/detail/'+id,
    method: 'get'
  })
}

// 查询书籍问题详细
export function getObj(id) {
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

// 新增书籍问题
export function addBoth(data) {
  return request({
    url: '/course/question/question-choice',
    method: 'post',
    data: data
  })
}

// 修改书籍问题
export function putBoth(data) {
  return request({
    url: '/course/question/question-choice',
    method: 'put',
    data: data
  })
}
