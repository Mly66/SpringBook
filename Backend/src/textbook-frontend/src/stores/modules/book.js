import { defineStore } from 'pinia'
import { getBookList, saveBook, getBook, deleteBook } from '@/api/book'

export const useBookStore = defineStore('book', {
  state: () => ({
    bookList: [],
    currentBook: null,
    pagination: {
      total: 0,
      current: 1,
      pageSize: 10
    },
    loading: false
  }),
  
  actions: {
    async fetchBookList(page = 1, size = 10) {
      this.loading = true
      
      try {
        const response = await getBookList(page, size)
        const { list, total, pageNum, pageSize } = response.data
        
        this.bookList = list
        this.pagination = {
          total,
          current: pageNum,
          pageSize
        }
        
        return Promise.resolve(response.data)
      } catch (error) {
        return Promise.reject(error)
      } finally {
        this.loading = false
      }
    },
    
    async fetchBookDetail(bookId) {
      this.loading = true
      
      try {
        const response = await getBook(bookId)
        this.currentBook = response.data
        return Promise.resolve(response.data)
      } catch (error) {
        return Promise.reject(error)
      } finally {
        this.loading = false
      }
    },
    
    async saveBook(bookData) {
      try {
        const response = await saveBook(bookData)
        return Promise.resolve(response)
      } catch (error) {
        return Promise.reject(error)
      }
    },
    
    async removeBook(bookId) {
      try {
        const response = await deleteBook(bookId)
        return Promise.resolve(response)
      } catch (error) {
        return Promise.reject(error)
      }
    }
  }
})
