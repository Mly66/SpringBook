<template>
  <div class="publisher-form-container">
    <div class="page-header">
      <h2>{{ isEdit ? '编辑出版社' : '添加出版社' }}</h2>
    </div>
    
    <el-card class="box-card">
      <el-form
        ref="publisherFormRef"
        :model="publisherForm"
        :rules="publisherRules"
        label-width="100px"
      >
        <el-form-item label="出版社名称" prop="pubName">
          <el-input v-model="publisherForm.pubName" />
        </el-form-item>
        
        <el-form-item label="联系人" prop="contacter">
          <el-input v-model="publisherForm.contacter" />
        </el-form-item>
        
        <el-form-item label="联系电话" prop="mobile">
          <el-input v-model="publisherForm.mobile" />
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
import { usePublisherStore } from '@/stores/modules/publisher'

export default {
  name: 'PublisherForm',
  setup() {
    const route = useRoute()
    const router = useRouter()
    const publisherStore = usePublisherStore()
    
    const publisherFormRef = ref(null)
    const loading = ref(false)
    
    const pubId = computed(() => {
      return route.params.id ? parseInt(route.params.id) : 0
    })
    
    const isEdit = computed(() => pubId.value > 0)
    
    const publisherForm = reactive({
      pubId: 0,
      pubName: '',
      contacter: '',
      mobile: ''
    })
    
    const publisherRules = {
      pubName: [
        { required: true, message: '请输入出版社名称', trigger: 'blur' }
      ],
      contacter: [
        { required: true, message: '请输入联系人', trigger: 'blur' }
      ],
      mobile: [
        { required: true, message: '请输入联系电话', trigger: 'blur' },
        { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
      ]
    }
    
    const fetchPublisherDetail = async () => {
      if (!isEdit.value) return
      
      loading.value = true
      try {
        const publisherData = await publisherStore.fetchPublisherDetail(pubId.value)
        Object.assign(publisherForm, publisherData)
      } catch (error) {
        ElMessage.error('获取出版社信息失败')
      } finally {
        loading.value = false
      }
    }
    
    const handleSubmit = () => {
      publisherFormRef.value.validate(async valid => {
        if (!valid) return
        
        loading.value = true
        try {
          await publisherStore.savePublisher(publisherForm)
          ElMessage.success(isEdit.value ? '更新成功' : '添加成功')
          router.push('/admin/publishers')
        } catch (error) {
          ElMessage.error(isEdit.value ? '更新失败' : '添加失败')
        } finally {
          loading.value = false
        }
      })
    }
    
    const handleCancel = () => {
      router.push('/admin/publishers')
    }
    
    onMounted(() => {
      if (isEdit.value) {
        fetchPublisherDetail()
      }
    })
    
    return {
      publisherFormRef,
      publisherForm,
      publisherRules,
      loading,
      isEdit,
      handleSubmit,
      handleCancel
    }
  }
}
</script>

<style scoped>
.publisher-form-container {
  padding: 20px;
}

.page-header {
  margin-bottom: 20px;
}

.box-card {
  max-width: 800px;
}
</style>
