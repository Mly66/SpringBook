<template>
  <div class="app-header">
    <div class="left">
      <el-button type="text" @click="toggleSidebar">
        <i class="el-icon-menu"></i>
      </el-button>
      <breadcrumb />
    </div>
    <div class="right">
      <el-dropdown trigger="click" @command="handleCommand">
        <span class="el-dropdown-link">
          {{ loginName }}<i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="password">修改密码</el-dropdown-item>
            <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessageBox } from 'element-plus'
import { useAuthStore } from '@/stores/modules/auth'
import Breadcrumb from './Breadcrumb.vue'

export default {
  name: 'AppHeader',
  components: {
    Breadcrumb
  },
  emits: ['toggle-sidebar'],
  setup(props, { emit }) {
    const router = useRouter()
    const authStore = useAuthStore()
    
    const loginName = computed(() => authStore.loginName)
    
    const toggleSidebar = () => {
      emit('toggle-sidebar')
    }
    
    const handleCommand = async (command) => {
      if (command === 'logout') {
        try {
          await ElMessageBox.confirm('确认退出登录吗?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          })
          
          await authStore.logout()
          router.push('/login')
        } catch (error) {
          // 用户取消操作
        }
      } else if (command === 'password') {
        const role = authStore.isAdmin || authStore.isBusinessAdmin ? 'admin' : 'teacher'
        router.push(`/${role}/change-password`)
      }
    }
    
    return {
      loginName,
      toggleSidebar,
      handleCommand
    }
  }
}
</script>

<style scoped>
.app-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
  padding: 0 15px;
}

.left {
  display: flex;
  align-items: center;
}

.right {
  display: flex;
  align-items: center;
}

.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
}
</style>
