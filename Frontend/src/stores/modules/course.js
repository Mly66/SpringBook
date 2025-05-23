import { defineStore } from 'pinia'
import { saveCourse, getCourse, deleteCourse } from '@/api/course'

export const useCourseStore = defineStore('course', {
  state: () => ({
    courseList: [],
    currentCourse: null,
    loading: false
  }),
  
  actions: {
    async fetchCourseDetail(courseId) {
      this.loading = true
      
      try {
        const response = await getCourse(courseId)
        this.currentCourse = response.data
        return Promise.resolve(response.data)
      } catch (error) {
        return Promise.reject(error)
      } finally {
        this.loading = false
      }
    },
    
    async saveCourse(courseData) {
      try {
        const response = await saveCourse(courseData)
        return Promise.resolve(response)
      } catch (error) {
        return Promise.reject(error)
      }
    },
    
    async removeCourse(courseId) {
      try {
        const response = await deleteCourse(courseId)
        return Promise.resolve(response)
      } catch (error) {
        return Promise.reject(error)
      }
    }
  }
})
