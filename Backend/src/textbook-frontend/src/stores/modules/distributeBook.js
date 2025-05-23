import { defineStore } from 'pinia'
import { distributeBook, getDistributeHistory, distributeFromApply } from '@/api/distributeBook'

export const useDistributeBookStore = defineStore('distributeBook', {
  state: () => ({
    distributeList: [],
    loading: false
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
    }
  }
})
