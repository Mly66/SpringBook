import { createRouter, createWebHistory } from 'vue-router'
import routes from './routes'
import { setupRouteGuards } from './guards'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { top: 0 }
    }
  }
})

setupRouteGuards(router)

export default router
