import request from '@/utils/request'

// 查询书籍问题选项列表
export function listChoice(query) {
  return request({
    url: '/course/choice/page',
    method: 'get',
    params: query
  })
}

// 查询书籍问题选项详细
export function getChoice(id) {
  return request({
    url: '/course/choice/' + id,
    method: 'get'
  })
}

// 新增书籍问题选项
export function addChoice(data) {
  return request({
    url: '/course/choice',
    method: 'post',
    data: data
  })
}

// 修改书籍问题选项
export function updateChoice(data) {
  return request({
    url: '/course/choice',
    method: 'put',
    data: data
  })
}

// 删除书籍问题选项
export function delChoice(id) {
  return request({
    url: '/course/choice/' + id,
    method: 'delete'
  })
}

// 导出书籍问题选项
export function exportChoice(query) {
  return request({
    url: '/course/choice/export',
    method: 'get',
    params: query
  })
}
