<template>
  <div class="layout-container">
    <el-container>
      <el-aside width="220px">
        <app-sidebar />
      </el-aside>
      <el-container>
        <el-header>
          <app-header />
        </el-header>
        <el-main>
          <router-view />
        </el-main>
        <el-footer>
          <app-footer />
        </el-footer>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import AppHeader from '@/components/common/AppHeader.vue'
import AppSidebar from '@/components/common/AppSidebar.vue'
import AppFooter from '@/components/common/AppFooter.vue'

export default {
  name: 'AdminLayout',
  components: {
    AppHeader,
    AppSidebar,
    AppFooter
  }
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
  background-color: #f0f2f5;
}

.el-header {
  background-color: #fff;
  padding: 0;
  line-height: 60px;
  position: relative;
  z-index: 10;
}

.el-aside {
  background-color: #001529;
  color: #fff;
  transition: width 0.3s;
  box-shadow: 2px 0 8px 0 rgba(29, 35, 41, 0.05);
  position: relative;
  z-index: 11;
}

.el-main {
  background-color: #f0f2f5;
  padding: 24px;
  min-height: calc(100vh - 120px);
}

.el-footer {
  background-color: #fff;
  color: #666;
  text-align: center;
  line-height: 60px;
  border-top: 1px solid #f0f0f0;
  font-size: 14px;
}

:deep(.el-menu) {
  border-right: none;
}

:deep(.el-menu-item) {
  height: 50px;
  line-height: 50px;
  margin: 4px 0;
  padding: 0 20px !important;
}

:deep(.el-menu-item.is-active) {
  background-color: #1890ff !important;
  color: #fff !important;
}

:deep(.el-menu-item:hover) {
  background-color: #001f3f !important;
}

:deep(.el-menu-item i) {
  margin-right: 10px;
  font-size: 18px;
}
</style>
