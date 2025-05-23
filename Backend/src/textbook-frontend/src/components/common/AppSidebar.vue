<template>
  <div class="sidebar-container">
    <el-menu
      :default-active="activeMenu"
      class="sidebar-menu"
      background-color="#304156"
      text-color="#bfcbd9"
      active-text-color="#409EFF"
      :collapse="isCollapse"
      :unique-opened="true"
      router
    >
      <el-menu-item v-if="isTeacher" index="/teacher/dashboard">
        <i class="el-icon-s-home"></i>
        <span>仪表盘</span>
      </el-menu-item>
      <el-menu-item v-else index="/admin/dashboard">
        <i class="el-icon-s-home"></i>
        <span>仪表盘</span>
      </el-menu-item>
      
      <!-- 管理员菜单 -->
      <template v-if="!isTeacher">
        <el-menu-item index="/admin/users">
          <i class="el-icon-user"></i>
          <span>用户管理</span>
        </el-menu-item>
        
        <el-menu-item index="/admin/courses">
          <i class="el-icon-reading"></i>
          <span>课程管理</span>
        </el-menu-item>
        
        <el-menu-item index="/admin/publishers">
          <i class="el-icon-office-building"></i>
          <span>出版社管理</span>
        </el-menu-item>
        
        <el-menu-item index="/admin/books">
          <i class="el-icon-notebook-1"></i>
          <span>图书管理</span>
        </el-menu-item>
        
        <el-menu-item index="/admin/approvals">
          <i class="el-icon-s-check"></i>
          <span>申请审批</span>
        </el-menu-item>
        
        <el-menu-item index="/admin/storage">
          <i class="el-icon-box"></i>
          <span>入库管理</span>
        </el-menu-item>
        
        <el-menu-item index="/admin/distribute">
          <i class="el-icon-s-promotion"></i>
          <span>教材发放</span>
        </el-menu-item>
        
        <el-menu-item index="/admin/distribute/history">
          <i class="el-icon-s-data"></i>
          <span>发放历史</span>
        </el-menu-item>
      </template>
      
      <!-- 教师菜单 -->
      <template v-else>
        <el-menu-item index="/teacher/books">
          <i class="el-icon-notebook-1"></i>
          <span>教材申请</span>
        </el-menu-item>
        
        <el-menu-item index="/teacher/applications">
          <i class="el-icon-s-order"></i>
          <span>我的申请</span>
        </el-menu-item>
      </template>
      
      <!-- 公共菜单 -->
      <el-menu-item v-if="isTeacher" index="/teacher/change-password">
        <i class="el-icon-lock"></i>
        <span>修改密码</span>
      </el-menu-item>
      <el-menu-item v-else index="/admin/change-password">
        <i class="el-icon-lock"></i>
        <span>修改密码</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'

export default {
  name: 'AppSidebar',
  props: {
    isTeacher: {
      type: Boolean,
      default: false
    }
  },
  setup() {
    const route = useRoute()
    const isCollapse = ref(false)
    
    const activeMenu = computed(() => {
      const { meta, path } = route
      if (meta.activeMenu) {
        return meta.activeMenu
      }
      return path
    })
    
    return {
      isCollapse,
      activeMenu
    }
  }
}
</script>

<style scoped>
.sidebar-container {
  height: 100%;
  overflow-y: auto;
}

.sidebar-menu {
  height: 100%;
  border-right: none;
}

.sidebar-menu:not(.el-menu--collapse) {
  width: 220px;
}
</style>
