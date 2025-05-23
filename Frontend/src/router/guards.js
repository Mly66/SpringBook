export const setupRouteGuards = (router) => {
  router.beforeEach((to, from, next) => {
    // 检查路由是否需要认证
    const requiresAuth = to.matched.some(record => record.meta.requiresAuth)
    
    // 从sessionStorage获取登录信息
    const loginName = sessionStorage.getItem('loginName')
    const roleId = sessionStorage.getItem('roleId')
    
    // 如果需要认证但未登录，重定向到登录页
    if (requiresAuth && !loginName) {
      next({ name: 'Login', query: { redirect: to.fullPath } })
    } 
    // 如果已登录但访问登录页，重定向到首页
    else if (loginName && to.name === 'Login') {
      // 根据角色重定向到不同的仪表盘
      if (roleId === '1' || roleId === '2') {
        next({ name: 'AdminDashboard' })
      } else {
        next({ name: 'TeacherDashboard' })
      }
    }
    // 检查角色权限
    else if (to.meta.roles && !to.meta.roles.includes(Number(roleId))) {
      next({ name: 'Forbidden' })
    }
    else {
      next()
    }
  })
}
