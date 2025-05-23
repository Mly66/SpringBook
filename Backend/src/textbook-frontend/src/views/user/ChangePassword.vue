<template>
  <div class="change-password-container">
    <div class="page-header">
      <h2>修改密码</h2>
    </div>
    
    <el-card class="box-card">
      <el-form
        ref="passwordFormRef"
        :model="passwordForm"
        :rules="passwordRules"
        label-width="100px"
      >
        <el-form-item label="原密码" prop="oldPassword">
          <el-input v-model="passwordForm.oldPassword" type="password" show-password />
        </el-form-item>
        
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="passwordForm.newPassword" type="password" show-password />
        </el-form-item>
        
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="passwordForm.confirmPassword" type="password" show-password />
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="loading">保存</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { useAuthStore } from '@/stores/modules/auth'
import { changePassword } from '@/api/user'

export default {
  name: 'ChangePassword',
  setup() {
    const authStore = useAuthStore()
    
    const passwordFormRef = ref(null)
    const loading = ref(false)
    
    const passwordForm = reactive({
      oldPassword: '',
      newPassword: '',
      confirmPassword: ''
    })
    
    const validateConfirmPassword = (rule, value, callback) => {
      if (value !== passwordForm.newPassword) {
        callback(new Error('两次输入的密码不一致'))
      } else {
        callback()
      }
    }
    
    const passwordRules = {
      oldPassword: [
        { required: true, message: '请输入原密码', trigger: 'blur' }
      ],
      newPassword: [
        { required: true, message: '请输入新密码', trigger: 'blur' },
        { min: 6, message: '密码长度不能小于6位', trigger: 'blur' }
      ],
      confirmPassword: [
        { required: true, message: '请确认新密码', trigger: 'blur' },
        { validator: validateConfirmPassword, trigger: 'blur' }
      ]
    }
    
    const handleSubmit = () => {
      passwordFormRef.value.validate(async valid => {
        if (!valid) return
        
        loading.value = true
        try {
          await changePassword(passwordForm.oldPassword, passwordForm.newPassword)
          ElMessage.success('密码修改成功')
          
          // 清空表单
          passwordForm.oldPassword = ''
          passwordForm.newPassword = ''
          passwordForm.confirmPassword = ''
          passwordFormRef.value.resetFields()
        } catch (error) {
          ElMessage.error(error.message || '密码修改失败')
        } finally {
          loading.value = false
        }
      })
    }
    
    return {
      passwordFormRef,
      passwordForm,
      passwordRules,
      loading,
      handleSubmit
    }
  }
}
</script>

<style scoped>
.change-password-container {
  padding: 20px;
}

.page-header {
  margin-bottom: 20px;
}

.box-card {
  max-width: 800px;
}
</style>
