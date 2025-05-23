<template>
  <div class="book-form-container">
    <div class="page-header">
      <h2>{{ isEdit ? '编辑图书' : '添加图书' }}</h2>
    </div>
    
    <el-card class="box-card">
      <el-form
        ref="bookFormRef"
        :model="bookForm"
        :rules="bookRules"
        label-width="100px"
      >
        <el-form-item label="图书名称" prop="bookName">
          <el-input v-model="bookForm.bookName" />
        </el-form-item>
        
        <el-form-item label="ISBN" prop="isbn">
          <el-input v-model="bookForm.isbn" />
        </el-form-item>
        
        <el-form-item label="作者" prop="author">
          <el-input v-model="bookForm.author" />
        </el-form-item>
        
        <el-form-item label="价格" prop="price">
          <el-input-number v-model="bookForm.price" :precision="2" :step="0.1" :min="0" />
        </el-form-item>
        
        <el-form-item label="出版日期" prop="pubDate">
          <el-date-picker
            v-model="bookForm.pubDate"
            type="date"
            placeholder="选择日期"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>
        
        <el-form-item label="入库日期" prop="warehousingDate">
          <el-date-picker
            v-model="bookForm.warehousingDate"
            type="date"
            placeholder="选择日期"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>
        
        <el-form-item label="库存" prop="inventory">
          <el-input-number v-model="bookForm.inventory" :min="0" :step="1" />
        </el-form-item>
        
        <el-form-item label="出版社" prop="pubId">
          <el-select v-model="bookForm.pubId" placeholder="请选择出版社">
            <el-option
              v-for="item in publisherOptions"
              :key="item.pubId"
              :label="item.pubName"
              :value="item.pubId"
            />
          </el-select>
        </el-form-item>
        
        <el-form-item label="课程" prop="courseId">
          <el-select v-model="bookForm.courseId" placeholder="请选择课程">
            <el-option
              v-for="item in courseOptions"
              :key="item.courseId"
              :label="item.courseName"
              :value="item.courseId"
            />
          </el-select>
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
import { useBookStore } from '@/stores/modules/book'
import { useMenuStore } from '@/stores/modules/menu'

export default {
  name: 'BookForm',
  setup() {
    const route = useRoute()
    const router = useRouter()
    const bookStore = useBookStore()
    const menuStore = useMenuStore()
    
    const bookFormRef = ref(null)
    const loading = ref(false)
    const publisherOptions = ref([])
    const courseOptions = ref([])
    
    const bookId = computed(() => {
      return route.params.id ? parseInt(route.params.id) : 0
    })
    
    const isEdit = computed(() => bookId.value > 0)
    
    const bookForm = reactive({
      bookId: 0,
      bookName: '',
      isbn: '',
      price: 0,
      pubDate: '',
      warehousingDate: '',
      author: '',
      inventory: 0,
      pubId: '',
      courseId: ''
    })
    
    const bookRules = {
      bookName: [
        { required: true, message: '请输入图书名称', trigger: 'blur' }
      ],
      isbn: [
        { required: true, message: '请输入ISBN', trigger: 'blur' }
      ],
      author: [
        { required: true, message: '请输入作者', trigger: 'blur' }
      ],
      price: [
        { required: true, message: '请输入价格', trigger: 'blur' }
      ],
      pubDate: [
        { required: true, message: '请选择出版日期', trigger: 'change' }
      ],
      warehousingDate: [
        { required: true, message: '请选择入库日期', trigger: 'change' }
      ],
      pubId: [
        { required: true, message: '请选择出版社', trigger: 'change' }
      ],
      courseId: [
        { required: true, message: '请选择课程', trigger: 'change' }
      ]
    }
    
    const fetchBookDetail = async () => {
      if (!isEdit.value) return
      
      loading.value = true
      try {
        const bookData = await bookStore.fetchBookDetail(bookId.value)
        Object.assign(bookForm, bookData)
      } catch (error) {
        ElMessage.error('获取图书信息失败')
      } finally {
        loading.value = false
      }
    }
    
    const fetchOptions = async () => {
      loading.value = true
      try {
        // 获取教材申请菜单数据，包含出版社和课程列表
        const response = await menuStore.fetchMenuData(6)
        publisherOptions.value = response.publishers || []
        courseOptions.value = response.courses || []
      } catch (error) {
        ElMessage.error('获取选项数据失败')
      } finally {
        loading.value = false
      }
    }
    
    const handleSubmit = () => {
      bookFormRef.value.validate(async valid => {
        if (!valid) return
        
        loading.value = true
        try {
          // 转换数据结构以匹配后端 API 要求
          const submitData = {
            ...bookForm,
            publisher: {
              pubId: bookForm.pubId
            },
            course: {
              courseId: bookForm.courseId
            }
          }
          // 删除不需要的字段
          delete submitData.pubId
          delete submitData.courseId
          delete submitData.warehousingDate // 后端不需要这个字段
          
          await bookStore.saveBook(submitData)
          ElMessage.success(isEdit.value ? '更新成功' : '添加成功')
          router.push('/admin/books')
        } catch (error) {
          ElMessage.error(isEdit.value ? '更新失败' : '添加失败')
        } finally {
          loading.value = false
        }
      })
    }
    
    const handleCancel = () => {
      router.push('/admin/books')
    }
    
    onMounted(() => {
      fetchOptions()
      if (isEdit.value) {
        fetchBookDetail()
      }
    })
    
    return {
      bookFormRef,
      bookForm,
      bookRules,
      loading,
      isEdit,
      publisherOptions,
      courseOptions,
      handleSubmit,
      handleCancel
    }
  }
}
</script>

<style scoped>
.book-form-container {
  padding: 20px;
}

.page-header {
  margin-bottom: 20px;
}

.box-card {
  max-width: 800px;
}
</style>
