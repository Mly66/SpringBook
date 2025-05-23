import request from '@/utils/request'

// 获取图书列表（分页）
export function getBookList(page = 1, size = 10) {
  return request({
    url: '/book/list',
    method: 'get',
    params: { page, size }
  })
}

// 保存图书信息（新增或更新）
export function saveBook(data) {
  return request({
    url: '/book/save',
    method: 'post',
    data
  })
}

// 获取图书详情
export function getBook(bookId) {
  return request({
    url: `/book/${bookId}`,
    method: 'get'
  })
}

// 删除图书
export function deleteBook(bookId) {
  return request({
    url: `/book/${bookId}`,
    method: 'delete'
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
