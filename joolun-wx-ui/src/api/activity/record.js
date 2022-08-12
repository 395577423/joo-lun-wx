import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listRecord(query) {
  return request({
    url: '/activity/share/record/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getRecord(id) {
  return request({
    url: '/activity/share/record/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addRecord(data) {
  return request({
    url: '/activity/share/record',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateRecord(data) {
  return request({
    url: '/activity/share/record',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delRecord(id) {
  return request({
    url: '/activity/share/record/' + id,
    method: 'delete'
  })
}

// 导出【请填写功能名称】
export function exportRecord(query) {
  return request({
    url: '/activity/share/record/export',
    method: 'get',
    params: query
  })
}
