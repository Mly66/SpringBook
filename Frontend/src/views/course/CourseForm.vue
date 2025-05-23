<template>
  <div class="course-form-container">
    <div class="page-header">
      <h2>{{ isEdit ? '编辑课程' : '添加课程' }}</h2>
    </div>
    
    <el-card class="box-card">
      <el-form
        ref="courseFormRef"
        :model="courseForm"
        :rules="courseRules"
        label-width="100px"
      >
        <el-form-item label="课程名称" prop="courseName">
          <el-input v-model="courseForm.courseName" />
        </el-form-item>
        
        <el-form-item label="学时" prop="period">
          <el-input-number v-model="courseForm.period" :min="0" :step="1" />
        </el-form-item>
        
        <el-form-item label="学分" prop="credit">
          <el-input-number v-model="courseForm.credit" :min="0" :step="1" />
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
import { useCourseStore } from '@/stores/modules/course'

export default {
  name: 'CourseForm',
  setup() {
    const route = useRoute()
    const router = useRouter()
    const courseStore = useCourseStore()
    
    const courseFormRef = ref(null)
    const loading = ref(false)
    
    const courseId = computed(() => {
      return route.params.id ? parseInt(route.params.id) : 0
    })
    
    const isEdit = computed(() => courseId.value > 0)
    
    const courseForm = reactive({
      courseId: 0,
      courseName: '',
      period: 0,
      credit: 0
    })
    
    const courseRules = {
      courseName: [
        { required: true, message: '请输入课程名称', trigger: 'blur' }
      ],
      period: [
        { required: true, message: '请输入学时', trigger: 'blur' }
      ],
      credit: [
        { required: true, message: '请输入学分', trigger: 'blur' }
      ]
    }
    
    const fetchCourseDetail = async () => {
      if (!isEdit.value) return
      
      loading.value = true
      try {
        const courseData = await courseStore.fetchCourseDetail(courseId.value)
        Object.assign(courseForm, courseData)
      } catch (error) {
        ElMessage.error('获取课程信息失败')
      } finally {
        loading.value = false
      }
    }
    
    const handleSubmit = () => {
      courseFormRef.value.validate(async valid => {
        if (!valid) return
        
        loading.value = true
        try {
          await courseStore.saveCourse(courseForm)
          ElMessage.success(isEdit.value ? '更新成功' : '添加成功')
          router.push('/admin/courses')
        } catch (error) {
          ElMessage.error(isEdit.value ? '更新失败' : '添加失败')
        } finally {
          loading.value = false
        }
      })
    }
    
    const handleCancel = () => {
      router.push('/admin/courses')
    }
    
    onMounted(() => {
      if (isEdit.value) {
        fetchCourseDetail()
      }
    })
    
    return {
      courseFormRef,
      courseForm,
      courseRules,
      loading,
      isEdit,
      handleSubmit,
      handleCancel
    }
  }
}
</script>

<style scoped>
.course-form-container {
  padding: 20px;
}

.page-header {
  margin-bottom: 20px;
}

.box-card {
  max-width: 800px;
}
</style>
