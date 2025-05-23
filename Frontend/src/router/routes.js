export default [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/Login.vue')
  },
  {
    path: '/admin',
    component: () => import('@/layout/AdminLayout.vue'),
    redirect: '/admin/dashboard',  // 修改默认重定向到仪表盘
    meta: { requiresAuth: true, roles: [1, 2] }, // 系统管理员和业务管理员
    children: [
      {
        path: 'dashboard',
        name: 'AdminDashboard',
        component: () => import('@/views/dashboard/AdminDashboard.vue'),
        meta: { title: '仪表盘', icon: 'el-icon-s-home' }
      },
      {
        path: 'users',
        name: 'UserList',
        component: () => import('@/views/user/UserList.vue'),
        meta: { title: '用户管理', icon: 'el-icon-user' }
      },
      {
        path: 'users/add',
        name: 'UserAdd',
        component: () => import('@/views/user/UserForm.vue'),
        meta: { title: '添加用户', activeMenu: '/admin/users' },
        hidden: true
      },
      {
        path: 'users/edit/:id',
        name: 'UserEdit',
        component: () => import('@/views/user/UserForm.vue'),
        meta: { title: '编辑用户', activeMenu: '/admin/users' },
        hidden: true
      },
      {
        path: 'courses',
        name: 'CourseList',
        component: () => import('@/views/course/CourseList.vue'),
        meta: { title: '课程管理', icon: 'el-icon-reading' }
      },
      {
        path: 'courses/add',
        name: 'CourseAdd',
        component: () => import('@/views/course/CourseForm.vue'),
        meta: { title: '添加课程', activeMenu: '/admin/courses' },
        hidden: true
      },
      {
        path: 'courses/edit/:id',
        name: 'CourseEdit',
        component: () => import('@/views/course/CourseForm.vue'),
        meta: { title: '编辑课程', activeMenu: '/admin/courses' },
        hidden: true
      },
      {
        path: 'publishers',
        name: 'PublisherList',
        component: () => import('@/views/publisher/PublisherList.vue'),
        meta: { title: '出版社管理', icon: 'el-icon-office-building' }
      },
      {
        path: 'publishers/add',
        name: 'PublisherAdd',
        component: () => import('@/views/publisher/PublisherForm.vue'),
        meta: { title: '添加出版社', activeMenu: '/admin/publishers' },
        hidden: true
      },
      {
        path: 'publishers/edit/:id',
        name: 'PublisherEdit',
        component: () => import('@/views/publisher/PublisherForm.vue'),
        meta: { title: '编辑出版社', activeMenu: '/admin/publishers' },
        hidden: true
      },
      {
        path: 'books',
        name: 'BookList',
        component: () => import('@/views/book/BookList.vue'),
        meta: { title: '图书管理', icon: 'el-icon-notebook-1' }
      },
      {
        path: 'books/add',
        name: 'BookAdd',
        component: () => import('@/views/book/BookForm.vue'),
        meta: { title: '添加图书', activeMenu: '/admin/books' },
        hidden: true
      },
      {
        path: 'books/edit/:id',
        name: 'BookEdit',
        component: () => import('@/views/book/BookForm.vue'),
        meta: { title: '编辑图书', activeMenu: '/admin/books' },
        hidden: true
      },
      {
        path: 'approvals',
        name: 'ApprovalList',
        component: () => import('@/views/apply/ApproveList.vue'),
        meta: { title: '申请审批', icon: 'el-icon-s-check' }
      },
      {
        path: 'storage',
        name: 'StorageList',
        component: () => import('@/views/apply/StorageList.vue'),
        meta: { title: '入库管理', icon: 'el-icon-box' }
      },
      {
        path: 'distribute',
        name: 'DistributeList',
        component: () => import('@/views/distribute/DistributeList.vue'),
        meta: { title: '教材发放', icon: 'el-icon-s-promotion' }
      },
      {
        path: 'distribute/history',
        name: 'DistributeHistory',
        component: () => import('@/views/distribute/DistributeHistory.vue'),
        meta: { title: '发放历史', icon: 'el-icon-s-data' }
      },
      {
        path: 'change-password',
        name: 'AdminChangePassword',
        component: () => import('@/views/user/ChangePassword.vue'),
        meta: { title: '修改密码', icon: 'el-icon-lock' }
      }
    ]
  },
  {
    path: '/teacher',
    component: () => import('@/layout/TeacherLayout.vue'),
    meta: { requiresAuth: true, roles: [3] }, // 教师角色
    children: [
      {
        path: 'dashboard',
        name: 'TeacherDashboard',
        component: () => import('@/views/dashboard/TeacherDashboard.vue'),
        meta: { title: '仪表盘', icon: 'el-icon-s-home' }
      },
      {
        path: 'books',
        name: 'TeacherBookList',
        component: () => import('@/views/book/TeacherBookList.vue'),
        meta: { title: '教材申请', icon: 'el-icon-notebook-1' }
      },
      {
        path: 'applications',
        name: 'TeacherApplyList',
        component: () => import('@/views/apply/TeacherApplyList.vue'),
        meta: { title: '我的申请', icon: 'el-icon-s-order' }
      },
      {
        path: 'change-password',
        name: 'TeacherChangePassword',
        component: () => import('@/views/user/ChangePassword.vue'),
        meta: { title: '修改密码', icon: 'el-icon-lock' }
      }
    ]
  },
  {
    path: '/403',
    name: 'Forbidden',
    component: () => import('@/views/error/403.vue')
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('@/views/error/404.vue')
  }
]
