import {
  createRouter,
  createWebHistory
} from 'vue-router';

import { ElMessage } from 'element-plus';
import { useMainStore } from '@/store';

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/views/Register.vue')
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});


router.beforeEach((to, from, next) => {
  const store = useMainStore();

  if (!store.isAuthenticated && (to.path === '/login' || to.path === '/register')) {
    return next();
  }
  if (!store.isAuthenticated) {
    return next('/login');
  }

  next();
});

export default router;