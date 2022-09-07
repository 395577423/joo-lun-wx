import request from '@/utils/request'

// 查询会员价格设置列表
export function listConfig(query) {
  return request({
    url: '/vip/config/list',
    method: 'get',
    params: query
  })
}

// 查询会员价格设置详细
export function getConfig(id) {
  return request({
    url: '/vip/config/' + id,
    method: 'get'
  })
}

// 新增会员价格设置
export function addConfig(data) {
  return request({
    url: '/vip/config',
    method: 'post',
    data: data
  })
}

// 修改会员价格设置
export function updateConfig(data) {
  return request({
    url: '/vip/config',
    method: 'put',
    data: data
  })
}

// 删除会员价格设置
export function delConfig(id) {
  return request({
    url: '/vip/config/' + id,
    method: 'delete'
  })
}
