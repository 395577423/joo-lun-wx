import request from '@/utils/request'

// 查询书籍问题选项列表
export function getChoicePage(query) {
  return request({
    url: '/course/choice/page',
    method: 'get',
    params: query
  })
}

// 查询书籍问题选项详细
export function getChoiceObj(id) {
  return request({
    url: '/course/choice/' + id,
    method: 'get'
  })
}

// 新增书籍问题选项
export function addChoiceObj(data) {
  return request({
    url: '/course/choice',
    method: 'post',
    data: data
  })
}

// 修改书籍问题选项
export function putChoiceObj(data) {
  return request({
    url: '/course/choice',
    method: 'put',
    data: data
  })
}

// 删除书籍问题选项
export function delChoiceObj(id) {
  return request({
    url: '/course/choice/' + id,
    method: 'delete'
  })
}

// 删除书籍问题选项
export function listByQuestion(id) {
  return request({
    url: '/course/choice/list/' + id,
    method: 'get'
  })
}
