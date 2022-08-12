import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listCourse(query) {
  return request({
    url: '/system/course/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getCourse(id) {
  return request({
    url: '/system/course/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addCourse(data) {
  return request({
    url: '/system/course',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateCourse(data) {
  return request({
    url: '/system/course',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delCourse(id) {
  return request({
    url: '/system/course/' + id,
    method: 'delete'
  })
}

// 导出【请填写功能名称】
export function exportCourse(query) {
  return request({
    url: '/system/course/export',
    method: 'get',
    params: query
  })
}