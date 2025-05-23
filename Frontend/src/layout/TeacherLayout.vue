<template>
  <div class="layout-container">
    <el-container>
      <el-aside width="220px">
        <app-sidebar :is-teacher="true" />
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
  name: 'TeacherLayout',
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
}

.el-header {
  background-color: #fff;
  border-bottom: 1px solid #e6e6e6;
  padding: 0;
  line-height: 60px;
}

.el-aside {
  background-color: #304156;
  color: #fff;
}

.el-main {
  background-color: #f0f2f5;
  padding: 20px;
}

.el-footer {
  background-color: #fff;
  color: #666;
  text-align: center;
  line-height: 60px;
}
</style>
