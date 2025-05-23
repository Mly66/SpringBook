import { defineStore } from 'pinia'
import { saveUser, getUser, deleteUser, resetPassword, getCurrentUser } from '@/api/user'
import { useMenuStore } from './menu'

export const useUserStore = defineStore('user', {
  state: () => ({
    userList: [],
    currentUser: null,
    loading: false,
    total: 0
  }),
  
  actions: {
    async fetchUserDetail(userId) {
      this.loading = true
      
      try {
        const response = await getUser(userId)
        this.currentUser = response.data
        return Promise.resolve(response.data)
      } catch (error) {
        return Promise.reject(error)
      } finally {
        this.loading = false
      }
    },
    
    async saveUser(userData, roleIds) {
      try {
        const response = await saveUser(userData, roleIds)
        return Promise.resolve(response)
      } catch (error) {
        return Promise.reject(error)
      }
    },
    
    async removeUser(userId) {
      try {
        const response = await deleteUser(userId)
        return Promise.resolve(response)
      } catch (error) {
        return Promise.reject(error)
      }
    },
    
    async resetUserPassword(userId) {
      try {
        const response = await resetPassword(userId)
        return Promise.resolve(response)
      } catch (error) {
        return Promise.reject(error)
      }
    },
    
    async getUserCount() {
      try {
        const menuStore = useMenuStore()
        const response = await menuStore.fetchMenuData(1, 1)
        return response.total || 0
      } catch (error) {
        console.error('获取用户总数失败:', error)
        return 0
      }
    }
  }
})
