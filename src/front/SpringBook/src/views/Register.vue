<template>
  <div class="register-container">
    <div class="application">
      <div class="application-content">
        <div class="icon-book"></div>
        <span>简易教材发放系统</span>
      </div>
    </div>
    <div class="controls">
      <div class="caret"></div>
      <div class="form-wrapper">
        <h1 class="text-center">注册</h1>
        <el-form :model="registerForm" :rules="rules" ref="registerFormRef">
          <el-form-item prop="loginName">
            <el-input 
              v-model="registerForm.loginName" 
              placeholder="登录名"
              :prefix-icon="User"
            />
          </el-form-item>
          <el-form-item prop="password">
            <el-input 
              v-model="registerForm.password" 
              type="password" 
              placeholder="密码"
              :prefix-icon="Lock"
            />
          </el-form-item>
          <el-form-item prop="confirmPassword">
            <el-input 
              v-model="registerForm.confirmPassword" 
              type="password" 
              placeholder="确认密码"
              :prefix-icon="Lock"
            />
          </el-form-item>
          <el-form-item prop="userName">
            <el-input 
              v-model="registerForm.userName" 
              placeholder="用户名"
              :prefix-icon="UserFilled"
            />
          </el-form-item>
          <el-form-item prop="mobile">
            <el-input 
              v-model="registerForm.mobile" 
              placeholder="手机号"
              :prefix-icon="Phone"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleRegister" :loading="loading" style="width: 100%">
              注册
            </el-button>
          </el-form-item>
          <el-form-item v-if="registerError">
            <div class="error-message">{{ registerError }}</div>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <div class="login-action text-center">
      <el-link type="primary" @click="$router.push('/login')">
        <el-icon><User /></el-icon>登录
      </el-link>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { User, Lock, UserFilled, Phone } from '@element-plus/icons-vue';
import { register } from '@/api';

const router = useRouter();
const registerFormRef = ref(null);
const loading = ref(false);
const registerError = ref('');

const registerForm = reactive({
  loginName: '',
  password: '',
  confirmPassword: '',
  userName: '',
  mobile: ''
});

const validatePass = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入密码'));
  } else {
    if (registerForm.confirmPassword !== '') {
      registerFormRef.value?.validateField('confirmPassword');
    }
    callback();
  }
};

const validatePass2 = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'));
  } else if (value !== registerForm.password) {
    callback(new Error('两次输入密码不一致!'));
  } else {
    callback();
  }
};

const validateMobile = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入手机号'));
  } else if (!/^1[3-9]\d{9}$/.test(value)) {
    callback(new Error('请输入正确的手机号'));
  } else {
    callback();
  }
};

const rules = {
  loginName: [
    { required: true, message: '请输入登录名', trigger: 'blur' }
  ],
  password: [
    { required: true, validator: validatePass, trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, validator: validatePass2, trigger: 'blur' }
  ],
  userName: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  mobile: [
    { required: true, validator: validateMobile, trigger: 'blur' }
  ]
};

const handleRegister = async () => {
  if (!registerFormRef.value) return;
  
  await registerFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true;
      registerError.value = '';
      try {
        const { confirmPassword, ...registerData } = registerForm;
        const response = await register(registerData);
        if (response.data.code === 200) {
          ElMessage.success('注册成功');
          router.push('/login');
        } else {
          registerError.value = response.data.message || '注册失败';
        }
      } catch (error) {
        registerError.value = '注册失败，请稍后重试';
      } finally {
        loading.value = false;
      }
    }
  });
};
</script>

<style scoped>
.register-container {
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