import request from '@/utils/request'

// 查询学习奖励计划列表
export function listPlan(query) {
  return request({
    url: '/mall/plan/list',
    method: 'get',
    params: query
  })
}

// 查询学习奖励计划详细
export function getPlan(id) {
  return request({
    url: '/mall/plan/' + id,
    method: 'get'
  })
}

// 新增学习奖励计划
export function addPlan(data) {
  return request({
    url: '/mall/plan',
    method: 'post',
    data: data
  })
}

// 修改学习奖励计划
export function updatePlan(data) {
  return request({
    url: '/mall/plan',
    method: 'put',
    data: data
  })
}

// 删除学习奖励计划
export function delPlan(id) {
  return request({
    url: '/mall/plan/' + id,
    method: 'delete'
  })
}

// 导出学习奖励计划
export function exportPlan(query) {
  return request({
    url: '/mall/plan/export',
    method: 'get',
    params: query
  })
}