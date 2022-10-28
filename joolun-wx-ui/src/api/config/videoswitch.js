import request from '@/utils/request'

// 查询开关列表
export function listObj(query) {
  return request({
    url: '/switch/page',
    method: 'get',
    params: query
  })
}

// 查询开关详细
export function getObj(id) {
  return request({
    url: '/switch/' + id,
    method: 'get'
  })
}

// 修改开关
export function putObj(data) {
  return request({
    url: '/switch',
    method: 'put',
    data: data
  })
}
