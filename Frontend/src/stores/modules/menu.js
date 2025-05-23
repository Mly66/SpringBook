import { defineStore } from 'pinia'
import { getMenuData } from '@/api/menu'

export const useMenuStore = defineStore('menu', {
  state: () => ({
    menuData: {},
    loading: false,
    currentMenuType: null
  }),
  
  actions: {
    async fetchMenuData(menuType, pageNum = 1, pageSize = 10) {
      this.loading = true
      this.currentMenuType = menuType
      
      try {
        const response = await getMenuData(menuType, pageNum, pageSize)
        this.menuData = response.data
        return Promise.resolve(response.data)
      } catch (error) {
        return Promise.reject(error)
      } finally {
        this.loading = false
      }
    }
  }
})
