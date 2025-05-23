<template>
  <div class="login-container">
    <el-card class="login-card">
      <div class="login-title">SpringBook</div>
      <el-form :model="loginForm" :rules="loginRules" ref="loginFormRef" label-width="0" class="login-form">
        <el-form-item prop="loginName">
          <el-input v-model="loginForm.loginName" placeholder="用户名" prefix-icon="el-icon-user"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="loginForm.password" placeholder="密码" prefix-icon="el-icon-lock" show-password></el-input>
        </el-form-item>
        <el-form-item prop="roleId">
          <el-select v-model="loginForm.roleId" placeholder="请选择角色" style="width: 100%">
            <el-option :value="1" label="系统管理员"></el-option>
            <el-option :value="2" label="业务管理员"></el-option>
            <el-option :value="3" label="教师"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="loading" @click="handleLogin" style="width: 100%">登录</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { ref, reactive } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useAuthStore } from '@/stores/modules/auth'

export default {
  name: 'Login',
  setup() {
    const router = useRouter()
    const route = useRoute()
    const authStore = useAuthStore()
    const loginFormRef = ref(null)
    const loading = ref(false)
    
    const loginForm = reactive({
      loginName: '',
      password: '',
      roleId: ''
    })
    
    const loginRules = {
      loginName: [
        { required: true, message: '请输入用户名', trigger: 'blur' }
      ],
      password: [
        { required: true, message: '请输入密码', trigger: 'blur' }
      ],
      roleId: [
        { required: true, message: '请选择角色', trigger: 'change' }
      ]
    }
    
    const handleLogin = () => {
      loginFormRef.value.validate(async valid => {
        if (valid) {
          loading.value = true
          try {
            await authStore.login(loginForm)
            
            // 登录成功后根据角色跳转到不同的首页
            const redirectPath = route.query.redirect || ''
            if (redirectPath) {
              router.push(redirectPath)
            } else {
              if (loginForm.roleId === 1 || loginForm.roleId === 2) {
                router.push('/admin/dashboard')
              } else {
                router.push('/teacher/dashboard')
              }
            }
            
            ElMessage({
              message: '登录成功',
              type: 'success'
            })
          } catch (error) {
            ElMessage.error(error.message || '登录失败，请检查用户名和密码')
          } finally {
            loading.value = false
          }
        }
      })
    }
    
    return {
      loginFormRef,
      loginForm,
      loginRules,
      loading,
      handleLogin
    }
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, #1890ff 0%, #36cfc9 100%);
}

.login-card {
  width: 420px;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  padding: 30px;
}

.login-title {
  text-align: center;
  font-size: 28px;
  font-weight: 600;
  margin-bottom: 35px;
  color: #1890ff;
  letter-spacing: 1px;
}

.login-form {
  margin-top: 25px;
}

.login-form :deep(.el-input__wrapper) {
  box-shadow: 0 0 0 1px #dcdfe6 inset;
  transition: all 0.3s;
}

.login-form :deep(.el-input__wrapper:hover) {
  box-shadow: 0 0 0 1px #1890ff inset;
}

.login-form :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 0 0 1px #1890ff inset;
}

.login-form :deep(.el-button--primary) {
  background: linear-gradient(90deg, #1890ff 0%, #36cfc9 100%);
  border: none;
  height: 44px;
  font-size: 16px;
  font-weight: 500;
  letter-spacing: 1px;
  transition: all 0.3s;
}

.login-form :deep(.el-button--primary:hover) {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(24, 144, 255, 0.3);
}

.login-form :deep(.el-select .el-input__wrapper) {
  box-shadow: 0 0 0 1px #dcdfe6 inset;
}

.login-form :deep(.el-select .el-input__wrapper:hover) {
  box-shadow: 0 0 0 1px #1890ff inset;
}
</style>
