import { defineStore } from 'pinia'
import { savePublisher, getPublisher, deletePublisher } from '@/api/publisher'

export const usePublisherStore = defineStore('publisher', {
  state: () => ({
    publisherList: [],
    currentPublisher: null,
    loading: false
  }),
  
  actions: {
    async fetchPublisherDetail(pubId) {
      this.loading = true
      
      try {
        const response = await getPublisher(pubId)
        this.currentPublisher = response.data
        return Promise.resolve(response.data)
      } catch (error) {
        return Promise.reject(error)
      } finally {
        this.loading = false
      }
    },
    
    async savePublisher(publisherData) {
      try {
        const response = await savePublisher(publisherData)
        return Promise.resolve(response)
      } catch (error) {
        return Promise.reject(error)
      }
    },
    
    async removePublisher(pubId) {
      try {
        const response = await deletePublisher(pubId)
        return Promise.resolve(response)
      } catch (error) {
        return Promise.reject(error)
      }
    }
  }
})
