import request from '@/utils/request'

// 获取菜单数据
export function getMenuData(menuType, pageNum = 1, pageSize = 10) {
  return request({
    url: '/menu/data',
    method: 'get',
    params: { menuType, pageNum, pageSize }
  })
}
