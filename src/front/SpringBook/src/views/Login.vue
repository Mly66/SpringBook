<template>
  <div class="login-container">
    <div class="application">
      <div class="application-content">
        <div class="icon-book"></div>
        <span>简易教材发放系统</span>
      </div>
    </div>
    <div class="controls">
      <div class="caret"></div>
      <div class="form-wrapper">
        <h1 class="text-center">登录</h1>
        <el-form :model="loginForm" :rules="rules" ref="loginFormRef">
          <el-form-item prop="loginName">
            <el-input 
              v-model="loginForm.loginName" 
              placeholder="登录名"
              :prefix-icon="User"
            />
          </el-form-item>
          <el-form-item prop="password">
            <el-input 
              v-model="loginForm.password" 
              type="password" 
              placeholder="密码"
              :prefix-icon="Lock"
            />
          </el-form-item>
          <el-form-item prop="roleId">
            <el-select v-model="loginForm.roleId" placeholder="请选择角色" style="width: 100%">
              <el-option label="系统管理员" :value="1" />
              <el-option label="业务管理员" :value="2" />
              <el-option label="教师" :value="3" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleLogin" :loading="loading" style="width: 100%">
              登录
            </el-button>
          </el-form-item>
          <el-form-item v-if="loginError">
            <div class="error-message">{{ loginError }}</div>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <div class="login-action text-center">
      <el-link type="primary" @click="$router.push('/register')">
        <el-icon><User /></el-icon>注册新用户
      </el-link>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { User, Lock } from '@element-plus/icons-vue';
import { useMainStore } from '@/store';
import { login } from '@/api';

const router = useRouter();
const store = useMainStore();
const loginFormRef = ref(null);
const loading = ref(false);
const loginError = ref('');

const loginForm = reactive({
  loginName: 'admin',
  password: '888888',
  roleId: 1
});

const rules = {
  loginName: [
    { required: true, message: '请输入登录名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ],
  roleId: [
    { required: true, message: '请选择角色', trigger: 'change' }
  ]
};

const handleLogin = async () => {
  if (!loginFormRef.value) return;
  
  await loginFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true;
      loginError.value = '';
      try {
        const response = await login(loginForm);
        if (response.data.code === 200) {
          store.setToken(response.data.data);
          ElMessage.success('登录成功');
          router.push('/');
        } else {
          loginError.value = response.data.message || '登录失败';
        }
      } catch (error) {
        loginError.value = '登录失败，请稍后重试';
      } finally {
        loading.value = false;
      }
    }
  });
};
</script>

<style scoped>
.login-container {
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: #f5f5f5;
}

.application {
  margin-bottom: 20px;
  text-align: center;
}

.application-content {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  font-size: 24px;
  color: #409EFF;
}

.icon-book {
  width: 32px;
  height: 32px;
  background: url('@/assets/book-icon.png') no-repeat center;
  background-size: contain;
}

.controls {
  background: white;
  padding: 30px;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  width: 400px;
}

.form-wrapper h1 {
  margin-bottom: 30px;
  color: #409EFF;
}

.caret {
  position: absolute;
  top: -8px;
  left: 50%;
  transform: translateX(-50%);
  width: 0;
  height: 0;
  border-left: 8px solid transparent;
  border-right: 8px solid transparent;
  border-bottom: 8px solid white;
}

.login-action {
  margin-top: 20px;
}

.error-message {
  color: #F56C6C;
  text-align: center;
}

:deep(.el-input__wrapper) {
  box-shadow: 0 0 0 1px #DCDFE6 inset;
}

:deep(.el-input__wrapper:hover) {
  box-shadow: 0 0 0 1px #409EFF inset;
}

:deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 0 0 1px #409EFF inset;
}
</style> 