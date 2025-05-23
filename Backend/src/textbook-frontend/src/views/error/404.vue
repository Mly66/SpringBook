<template>
  <div class="error-container">
    <div class="error-content">
      <h1>404</h1>
      <h2>页面不存在</h2>
      <p>抱歉，您访问的页面不存在</p>
      <el-button type="primary" @click="goHome">返回首页</el-button>
    </div>
  </div>
</template>

<script>
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/modules/auth'

export default {
  name: '404',
  setup() {
    const router = useRouter()
    const authStore = useAuthStore()
    
    const goHome = () => {
      if (authStore.isLoggedIn) {
        if (authStore.isAdmin || authStore.isBusinessAdmin) {
          router.push('/admin/dashboard')
        } else {
          router.push('/teacher/dashboard')
        }
      } else {
        router.push('/login')
      }
    }
    
    return {
      goHome
    }
  }
}
</script>

<style scoped>
.error-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.error-content {
  text-align: center;
}

.error-content h1 {
  font-size: 72px;
  color: #E6A23C;
  margin: 0;
}

.error-content h2 {
  font-size: 24px;
  margin: 20px 0;
}

.error-content p {
  margin-bottom: 30px;
  color: #666;
}
</style>
