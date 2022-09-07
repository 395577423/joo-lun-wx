import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listOrder(query) {
  return request({
    url: '/vip/order/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getOrder(id) {
  return request({
    url: '/vip/order/' + id,
    method: 'get'
  })
}


// 导出【请填写功能名称】
export function exportOrder(query) {
  return request({
    url: '/vip/order/export',
    method: 'get',
    params: query
  })
}
