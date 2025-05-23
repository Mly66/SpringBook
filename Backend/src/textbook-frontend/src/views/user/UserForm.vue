<template>
  <div class="user-form-container">
    <div class="page-header">
      <h2>{{ isEdit ? '编辑用户' : '添加用户' }}</h2>
    </div>
    
    <el-card class="box-card">
      <el-form
        ref="userFormRef"
        :model="userForm"
        :rules="userRules"
        label-width="100px"
      >
        <el-form-item label="用户名" prop="loginName">
          <el-input v-model="userForm.loginName" :disabled="isEdit" />
        </el-form-item>
        
        <el-form-item label="姓名" prop="userName">
          <el-input v-model="userForm.userName" />
        </el-form-item>
        
        <el-form-item label="手机号" prop="mobile">
          <el-input v-model="userForm.mobile" />
        </el-form-item>
        
        <el-form-item label="角色" prop="roleIds">
          <el-checkbox-group v-model="userForm.roleIds">
            <el-checkbox :label="1">系统管理员</el-checkbox>
            <el-checkbox :label="2">业务管理员</el-checkbox>
            <el-checkbox :label="3">教师</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="loading">保存</el-button>
          <el-button @click="handleCancel">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { ref, reactive, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/modules/user'

export default {
  name: 'UserForm',
  setup() {
    const route = useRoute()
    const router = useRouter()
    const userStore = useUserStore()
    
    const userFormRef = ref(null)
    const loading = ref(false)
    
    const userId = computed(() => {
      return route.params.id ? parseInt(route.params.id) : 0
    })
    
    const isEdit = computed(() => userId.value > 0)
    
    const userForm = reactive({
      userId: 0,
      loginName: '',
      userName: '',
      mobile: '',
      roleIds: []
    })
    
    const userRules = {
      loginName: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
        { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
      ],
      userName: [
        { required: true, message: '请输入姓名', trigger: 'blur' }
      ],
      mobile: [
        { required: true, message: '请输入手机号', trigger: 'blur' },
        { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
      ],
      roleIds: [
        { type: 'array', required: true, message: '请至少选择一个角色', trigger: 'change' }
      ]
    }
    
    const fetchUserDetail = async () => {
      if (!isEdit.value) return
      
      loading.value = true
      try {
        const userData = await userStore.fetchUserDetail(userId.value)
        userForm.userId = userData.userId
        userForm.loginName = userData.loginName
        userForm.userName = userData.userName
        userForm.mobile = userData.mobile
        
        // 设置角色
        if (userData.roles && userData.roles.length > 0) {
          userForm.roleIds = userData.roles.map(role => role.roleId)
        }
      } catch (error) {
        ElMessage.error('获取用户信息失败')
      } finally {
        loading.value = false
      }
    }
    
    const handleSubmit = () => {
      userFormRef.value.validate(async valid => {
        if (!valid) return
        
        loading.value = true
        try {
          const formData = {
            userId: userForm.userId,
            loginName: userForm.loginName,
            userName: userForm.userName,
            mobile: userForm.mobile
          }
          
          await userStore.saveUser(formData, userForm.roleIds)
          ElMessage.success(isEdit.value ? '更新成功' : '添加成功')
          router.push('/admin/users')
        } catch (error) {
          ElMessage.error(isEdit.value ? '更新失败' : '添加失败')
        } finally {
          loading.value = false
        }
      })
    }
    
    const handleCancel = () => {
      router.push('/admin/users')
    }
    
    onMounted(() => {
      if (isEdit.value) {
        fetchUserDetail()
      }
    })
    
    return {
      userFormRef,
      userForm,
      userRules,
      loading,
      isEdit,
      handleSubmit,
      handleCancel
    }
  }
}
</script>

<style scoped>
.user-form-container {
  padding: 20px;
}

.page-header {
  margin-bottom: 20px;
}

.box-card {
  max-width: 800px;
}
</style>
