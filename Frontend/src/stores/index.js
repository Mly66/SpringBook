import { createPinia } from 'pinia'
import { useAuthStore } from './modules/auth'
import { useMenuStore } from './modules/menu'
import { useBookStore } from './modules/book'
import { useUserStore } from './modules/user'
import { useCourseStore } from './modules/course'
import { usePublisherStore } from './modules/publisher'
import { useApplyBookStore } from './modules/applyBook'
import { useDistributeBookStore } from './modules/distributeBook'

const pinia = createPinia()

export {
  useAuthStore,
  useMenuStore,
  useBookStore,
  useUserStore,
  useCourseStore,
  usePublisherStore,
  useApplyBookStore,
  useDistributeBookStore
}

export default pinia
