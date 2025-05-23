import { defineStore } from 'pinia'
import { distributeBook, getDistributeHistory, distributeFromApply } from '@/api/distributeBook'
import { useMenuStore } from './menu'

export const useDistributeBookStore = defineStore('distributeBook', {
  state: () => ({
    distributeList: [],
    total: 0
  }),
  
  actions: {
    async fetchDistributeHistory() {
      this.loading = true
      
      try {
        const response = await getDistributeHistory()
        this.distributeList = response.data
        return Promise.resolve(response.data)
      } catch (error) {
        return Promise.reject(error)
      } finally {
        this.loading = false
      }
    },
    
    async distributeBook(data) {
      try {
        const response = await distributeBook(data)
        return Promise.resolve(response)
      } catch (error) {
        return Promise.reject(error)
      }
    },
    
    async distributeFromApply(data) {
      try {
        const response = await distributeFromApply(data)
        return Promise.resolve(response)
      } catch (error) {
        return Promise.reject(error)
      }
    },
    
    async getPendingDistributeCount() {
      try {
        const menuStore = useMenuStore()
        const response = await menuStore.fetchMenuData(5, 1)
        return response.total || 0
      } catch (error) {
        console.error('获取待发放教材数失败:', error)
        return 0
      }
    },
    
    async getRecentDistributions(limit = 5) {
      try {
        const response = await getDistributeHistory()
        return (response.list || []).slice(0, limit)
      } catch (error) {
        console.error('获取最近发放记录失败:', error)
        return []
      }
    }
  }
})
