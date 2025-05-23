import request from '@/utils/request'

// 获取申请详情
export function getApplyBook(bookId) {
  return request({
    url: `/apply-book/${bookId}`,
    method: 'get'
  })
}

// 修改申请数量
export function editApplyCount(bookId, data) {
  return request({
    url: `/apply-book/${bookId}/count`,
    method: 'put',
    data
  })
}

// 审批申请
export function approveApply(bookId, data) {
  return request({
    url: `/apply-book/${bookId}/approve`,
    method: 'put',
    data
  })
}

// 入库处理
export function inStorageApply(bookId) {
  return request({
    url: `/apply-book/${bookId}/in-storage`,
    method: 'put'
  })
}

// 获取用户申请的图书列表
export function getUserApplications() {
  return request({
    url: '/apply-book/user-applications',
    method: 'get'
  })
}
