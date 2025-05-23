# 教材管理系统 - Vue3前端交付说明文档

## 项目概述

本项目是基于Vue3开发的教材管理系统前端，与SpringBoot后端完全对接，实现了教材申请、审批、入库、发放等全流程管理功能。前端采用Vue3 + Vite + Element Plus + Pinia技术栈，实现了响应式设计和良好的用户体验。

## 技术栈

- **框架**: Vue 3
- **构建工具**: Vite
- **UI组件库**: Element Plus
- **状态管理**: Pinia
- **路由**: Vue Router
- **HTTP请求**: Axios
- **CSS预处理器**: SCSS

## 项目结构

```
textbook-frontend/
├── public/                 # 静态资源
├── src/
│   ├── api/                # API接口封装
│   ├── assets/             # 静态资源
│   ├── components/         # 公共组件
│   ├── composables/        # 组合式API
│   ├── config/             # 配置文件
│   ├── directives/         # 自定义指令
│   ├── layout/             # 布局组件
│   ├── router/             # 路由配置
│   ├── stores/             # 状态管理
│   ├── utils/              # 工具函数
│   ├── views/              # 页面组件
│   ├── App.vue             # 根组件
│   └── main.js             # 入口文件
├── index.html              # HTML模板
├── package.json            # 项目依赖
└── vite.config.js          # Vite配置
```

## 功能模块

### 1. 用户认证与权限控制

- 登录/登出
- 基于角色的权限控制
- 路由守卫

### 2. 用户管理

- 用户列表
- 添加/编辑用户
- 重置密码
- 个人资料管理
- 修改密码

### 3. 图书管理

- 图书列表
- 添加/编辑图书
- 删除图书

### 4. 课程管理

- 课程列表
- 添加/编辑课程
- 删除课程

### 5. 出版社管理

- 出版社列表
- 添加/编辑出版社
- 删除出版社

### 6. 教材申请

- 教师申请教材
- 申请记录查询

### 7. 申请审批

- 管理员审批申请
- 审批记录查询

### 8. 教材入库

- 已审批教材入库
- 入库记录查询

### 9. 教材发放

- 教材发放
- 发放历史查询

### 10. 仪表盘

- 管理员仪表盘
- 教师仪表盘

## 角色与权限

系统包含三种角色：

1. **系统管理员(roleId=1)**: 拥有所有功能权限
2. **业务管理员(roleId=2)**: 拥有除用户管理外的所有业务功能权限
3. **教师(roleId=3)**: 拥有教材申请、查看个人申请记录等权限

## 后端API对接

前端已完整对接所有后端API，包括：

- UserController: 用户管理相关API
- BookController: 图书管理相关API
- CourseController: 课程管理相关API
- PublisherController: 出版社管理相关API
- ApplyBookController: 教材申请相关API
- DistributeBookController: 教材发放相关API
- MenuController: 菜单数据相关API

## 开发与部署

### 开发环境搭建

1. 安装依赖：
```bash
npm install
```

2. 启动开发服务器：
```bash
npm run dev
```

### 生产环境构建

```bash
npm run build
```

构建后的文件将生成在`dist`目录中，可直接部署到Web服务器。

## 注意事项

1. 前端默认连接后端API地址为`http://localhost:8080`，如需修改请在`src/utils/request.js`中更改`baseURL`。
2. 系统使用Session进行状态管理，请确保浏览器允许Cookie。
3. 初始管理员账号为`admin`，密码为`123456`。

## 浏览器兼容性

- Chrome (最新版)
- Firefox (最新版)
- Edge (最新版)
- Safari (最新版)

## 联系方式

如有任何问题或需要技术支持，请联系开发团队。
