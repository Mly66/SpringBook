import { defineStore } from 'pinia'
import { login, logout, getCurrentUser } from '@/api/user'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    loginName: sessionStorage.getItem('loginName') || '',
    roleId: sessionStorage.getItem('roleId') ? Number(sessionStorage.getItem('roleId')) : null,
    isLoggedIn: !!sessionStorage.getItem('loginName')
  }),
  
  getters: {
    isAdmin: (state) => state.roleId === 1,
    isBusinessAdmin: (state) => state.roleId === 2,
    isTeacher: (state) => state.roleId === 3,
    hasPermission: (state) => (roles) => {
      if (!roles || roles.length === 0) return true
      return roles.includes(state.roleId)
    }
  },
  
  actions: {
    async login(loginInfo) {
      try {
        const response = await login(loginInfo)
        const { loginName, roleId } = response.data
        
        this.loginName = loginName
        this.roleId = roleId
        this.isLoggedIn = true
        
        // 保存到sessionStorage
        sessionStorage.setItem('loginName', loginName)
        sessionStorage.setItem('roleId', roleId)
        
        return Promise.resolve(response)
      } catch (error) {
        return Promise.reject(error)
      }
    },
    
    async logout() {
      try {
        await logout()
        this.resetState()
        return Promise.resolve()
      } catch (error) {
        return Promise.reject(error)
      }
    },
    
    async fetchCurrentUser() {
      try {
        const response = await getCurrentUser()
        const { loginName, roleId } = response.data
        
        this.loginName = loginName
        this.roleId = roleId
        this.isLoggedIn = true
        
        // 保存到sessionStorage
        sessionStorage.setItem('loginName', loginName)
        sessionStorage.setItem('roleId', roleId)
        
        return Promise.resolve(response)
      } catch (error) {
        this.resetState()
        return Promise.reject(error)
      }
    },
    
    resetState() {
      this.loginName = ''
      this.roleId = null
      this.isLoggedIn = false
      
      // 清除sessionStorage
      sessionStorage.removeItem('loginName')
      sessionStorage.removeItem('roleId')
    }
  }
})
