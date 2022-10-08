import request from '@/utils/request'

// 查询订单列表
export function listShare(query) {
  return request({
    url: '/store/share/list',
    method: 'get',
    params: query
  })
}
