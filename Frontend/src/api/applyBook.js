import request from '@/utils/request'

// 获取申请列表
export function getApplyList(page = 1, size = 10) {
  return request({
    url: '/apply-book/list',
    method: 'get',
    params: { page, size }
  })
}

// 获取申请详情
export function getApplyBook(bookId) {
  return request({
    url: `/apply-book/${bookId}`,
    method: 'get'
  })
}

// 申请图书
export function applyBook(data) {
  return request({
    url: '/book/apply',
    method: 'post',
    data
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

// 获取用户申请列表
export function getUserApplyList() {
  return request({
    url: '/apply-book/user-applications',
    method: 'get'
  })
}
