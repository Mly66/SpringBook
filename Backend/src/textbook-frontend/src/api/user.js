import request from '@/utils/request'

// 用户登录
export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

// 用户登出
export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}

// 获取当前登录用户信息
export function getCurrentUser() {
  return request({
    url: '/user/current',
    method: 'get'
  })
}

// 获取用户信息
export function getUser(userId) {
  return request({
    url: `/user/${userId}`,
    method: 'get'
  })
}

// 保存用户信息（新增或更新）
export function saveUser(data, roleIds) {
  return request({
    url: '/user/save',
    method: 'post',
    data,
    params: { roleIds }
  })
}

// 删除用户
export function deleteUser(userId) {
  return request({
    url: `/user/${userId}`,
    method: 'delete'
  })
}

// 重置密码
export function resetPassword(userId) {
  return request({
    url: `/user/${userId}/reset-password`,
    method: 'put'
  })
}

// 修改密码
export function changePassword(data) {
  return request({
    url: '/user/change-password',
    method: 'put',
    data
  })
}

// 用户注册
export function register(data) {
  return request({
    url: '/user/register',
    method: 'post',
    data
  })
}
