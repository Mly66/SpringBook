import request from '@/utils/request'

// 保存出版社信息（新增或更新）
export function savePublisher(data) {
  return request({
    url: '/publisher/save',
    method: 'post',
    data
  })
}

// 获取出版社详情
export function getPublisher(pubId) {
  return request({
    url: `/publisher/${pubId}`,
    method: 'get'
  })
}

// 删除出版社
export function deletePublisher(pubId) {
  return request({
    url: `/publisher/${pubId}`,
    method: 'delete'
  })
}
