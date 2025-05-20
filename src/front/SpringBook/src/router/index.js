import {
  createRouter,
  createWebHistory
} from 'vue-router';

import { ElMessage } from 'element-plus';

const routes = [
];

const router = createRouter({
  history: createWebHistory(),
  routes
});


router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token');
  const userInfo = localStorage.getItem('userInfo');

  // if (userInfo) {
  //   const [userRole, username] = userInfo.split(':');
  //   let role = (userRole === '管理员') ? 'admin' : 'employee';
  //   if (role) {
  //     const allowedRoutes = rolePermissions[role];
  //     if (!allowedRoutes.includes(to.path)) {
  //       return next();
  //     } else {
  //       ElMessage.error('没有权限');
  //       return next('/login');
  //     }
  //   }
  // }

  if (!token && (to.path === '/login' || to.path === '/register')) {
    return next();
  }
  if (!token) {
    return next('/login');
  }

  next();
});

export default router;