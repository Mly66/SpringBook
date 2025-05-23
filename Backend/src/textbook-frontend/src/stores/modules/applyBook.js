import { defineStore } from 'pinia'
import { getApplyBook, editApplyCount, approveApply, inStorageApply, getUserApplications } from '@/api/applyBook'

export const useApplyBookStore = defineStore('applyBook', {
  state: () => ({
    applyList: [],
    currentApply: null,
    loading: false
  }),
  
  actions: {
    async fetchApplyDetail(bookId) {
      this.loading = true
      
      try {
        const response = await getApplyBook(bookId)
        this.currentApply = response.data
        return Promise.resolve(response.data)
      } catch (error) {
        return Promise.reject(error)
      } finally {
        this.loading = false
      }
    },
    
    async updateApplyCount(bookId, applyData) {
      try {
        const response = await editApplyCount(bookId, applyData)
        return Promise.resolve(response)
      } catch (error) {
        return Promise.reject(error)
      }
    },
    
    async approveApplication(bookId, applyData) {
      try {
        const response = await approveApply(bookId, applyData)
        return Promise.resolve(response)
      } catch (error) {
        return Promise.reject(error)
      }
    },
    
    async inStorage(bookId) {
      try {
        const response = await inStorageApply(bookId)
        return Promise.resolve(response)
      } catch (error) {
        return Promise.reject(error)
      }
    },

    async fetchUserApplications() {
      this.loading = true
      try {
        const response = await getUserApplications()
        this.applyList = response.data
        return Promise.resolve(response.data)
      } catch (error) {
        return Promise.reject(error)
      } finally {
        this.loading = false
      }
    }
  }
})
