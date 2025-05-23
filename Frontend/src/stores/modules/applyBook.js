import { defineStore } from 'pinia'
import { getApplyBook, applyBook, approveApply, inStorageApply, getApplyList, getUserApplyList } from '@/api/applyBook'
import { useMenuStore } from './menu'

export const useApplyBookStore = defineStore('applyBook', {
  state: () => ({
    applyList: [],
    currentApply: null,
    loading: false,
    total: 0,
    pagination: {
      total: 0,
      current: 1,
      pageSize: 10
    }
  }),
  
  actions: {
    async fetchApplyList(page = 1, size = 10) {
      this.loading = true
      
      try {
        const response = await getApplyList(page, size)
        if (!response.data) {
          throw new Error('返回数据格式错误')
        }
        
        const { list, total, pageNum, pageSize } = response.data
        
        // 验证数据类型
        if (!Array.isArray(list)) {
          throw new Error('申请列表数据格式错误')
        }
        
        // 处理申请状态文本并验证数据
        this.applyList = (list || []).map(item => {
          // 验证必要字段
          if (!item.book || typeof item.book.bookId !== 'number') {
            console.warn('申请记录数据不完整:', item)
          }
          
          return {
            ...item,
            applyStatusText: this.getApplyStatusText(item.applyStatus),
            // 确保 bookId 是数字类型
            book: {
              ...item.book,
              bookId: Number(item.book?.bookId) || 0
            }
          }
        })
        
        this.pagination = {
          total: Number(total) || 0,
          current: Number(pageNum) || 1,
          pageSize: Number(pageSize) || 10
        }
        
        return Promise.resolve(response.data)
      } catch (error) {
        console.error('获取申请列表失败:', error)
        return Promise.reject(error)
      } finally {
        this.loading = false
      }
    },

    async fetchApplyDetail(bookId) {
      this.loading = true
      
      try {
        const response = await getApplyBook(bookId)
        // 处理申请状态文本
        const applyData = response.data
        applyData.applyStatusText = this.getApplyStatusText(applyData.applyStatus)
        this.currentApply = applyData
        return Promise.resolve(applyData)
      } catch (error) {
        return Promise.reject(error)
      } finally {
        this.loading = false
      }
    },

    getApplyStatusText(status) {
      switch (status) {
        case 0: return '待审批'
        case 1: return '已通过'
        case 2: return '已拒绝'
        case 3: return '已入库'
        default: return '未知状态'
      }
    },

    async applyBook(applyData) {
      try {
        const response = await applyBook(applyData)
        return Promise.resolve(response)
      } catch (error) {
        return Promise.reject(error)
      }
    },

    async approveApplication(bookId, approveData) {
      try {
        const response = await approveApply(bookId, approveData)
        return Promise.resolve(response)
      } catch (error) {
        return Promise.reject(error)
      }
    },

    async inStorageApplication(bookId) {
      try {
        const response = await inStorageApply(bookId)
        return Promise.resolve(response)
      } catch (error) {
        return Promise.reject(error)
      }
    },

    async getPendingApprovalCount() {
      try {
        const menuStore = useMenuStore()
        const response = await menuStore.fetchMenuData(3, 1)
        return response.total || 0
      } catch (error) {
        console.error('获取待审批申请数失败:', error)
        return 0
      }
    },

    async getRecentApplications(limit = 5) {
      try {
        const menuStore = useMenuStore()
        const response = await menuStore.fetchMenuData(9, 1, limit)
        const sortedList = (response.list || []).sort((a, b) => {
          return new Date(b.applyDate) - new Date(a.applyDate)
        }).slice(0, limit)
        return sortedList
      } catch (error) {
        console.error('获取最近申请记录失败:', error)
        return []
      }
    },

    async fetchUserApplyList() {
      this.loading = true
      
      try {
        const response = await getUserApplyList()
        if (!response.data) {
          throw new Error('返回数据格式错误')
        }
        
        // 处理申请状态文本并验证数据
        this.applyList = (response.data || []).map(item => {
          // 验证必要字段
          if (!item.book || typeof item.book.bookId !== 'number') {
            console.warn('申请记录数据不完整:', item)
          }
          
          return {
            ...item,
            applyStatusText: this.getApplyStatusText(item.applyStatus),
            // 确保 bookId 是数字类型
            book: {
              ...item.book,
              bookId: Number(item.book?.bookId) || 0
            }
          }
        })
        
        return Promise.resolve(response.data)
      } catch (error) {
        console.error('获取用户申请列表失败:', error)
        return Promise.reject(error)
      } finally {
        this.loading = false
      }
    }
  }
})
