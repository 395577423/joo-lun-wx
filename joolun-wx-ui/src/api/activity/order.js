import request from '@/utils/request'

// 查询订单列表
export function listOrder(query) {
  return request({
    url: '/activity/order/list',
    method: 'get',
    params: query
  })
}

// 查询订单详细
export function getOrder(id) {
  return request({
    url: '/activity/order/' + id,
    method: 'get'
  })
}

// 新增订单
export function addOrder(data) {
  return request({
    url: '/activity/order',
    method: 'post',
    data: data
  })
}

// 修改订单
export function updateOrder(data) {
  return request({
    url: '/activity/order',
    method: 'put',
    data: data
  })
}

// 删除订单
export function delOrder(id) {
  return request({
    url: '/activity/order/' + id,
    method: 'delete'
  })
}

// 导出订单
export function exportOrder(query) {
  return request({
    url: '/activity/order/export',
    method: 'get',
    params: query
  })
}

// 出行人信息
export function getPerson(activityId) {
  return request({
    url: '/activity/order/getPersons',
    method: 'get',
    params: {activityOrderId:activityId}
  })
}

//订单完成

export function completeOrder(id) {
  return request({
    url: '/activity/order/complete',
    method: 'get',
    params: {id:id}
  })
}
