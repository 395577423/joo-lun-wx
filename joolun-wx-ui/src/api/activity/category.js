import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function pageListCategory(query) {
  return request({
    url: '/activity/category/page',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getCategory(id) {
  return request({
    url: '/activity/category/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addCategory(data) {
  return request({
    url: '/activity/category',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateCategory(data) {
  return request({
    url: '/activity/category',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delCategory(id) {
  return request({
    url: '/activity/category/' + id,
    method: 'delete'
  })
}

// 导出【请填写功能名称】
export function exportCategory(query) {
  return request({
    url: '/activity/category/export',
    method: 'get',
    params: query
  })
}
