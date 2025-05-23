import request from '@/utils/request'

// 发放教材
export function distributeBook(data) {
  return request({
    url: '/distribute-book/distribute',
    method: 'post',
    data
  })
}

// 获取发放历史
export function getDistributeHistory() {
  return request({
    url: '/distribute-book/history',
    method: 'get'
  })
}

// 从申请记录发放教材
export function distributeFromApply(data) {
  return request({
    url: '/distribute-book/distribute-from-apply',
    method: 'post',
    data
  })
}
