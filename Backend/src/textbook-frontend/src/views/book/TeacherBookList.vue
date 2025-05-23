<template>
  <div class="teacher-book-list-container">
    <div class="page-header">
      <h2>教材申请</h2>
    </div>
    
    <el-card class="box-card">
      <div class="search-bar">
        <el-input
          v-model="searchQuery"
          placeholder="搜索图书名称或ISBN"
          clearable
          @clear="handleSearch"
          style="width: 300px;"
        >
          <template #append>
            <el-button @click="handleSearch">搜索</el-button>
          </template>
        </el-input>
      </div>
      
      <el-table
        :data="bookList"
        border
        style="width: 100%"
        v-loading="loading"
      >
        <el-table-column prop="bookId" label="ID" width="80" />
        <el-table-column prop="bookName" label="图书名称" width="200" />
        <el-table-column prop="isbn" label="ISBN" width="150" />
        <el-table-column prop="author" label="作者" width="120" />
        <el-table-column prop="price" label="价格" width="100">
          <template #default="scope">
            {{ scope.row.price }} 元
          </template>
        </el-table-column>
        <el-table-column prop="pubDate" label="出版日期" width="120" />
        <el-table-column prop="inventory" label="库存" width="80" />
        <el-table-column label="操作" width="120">
          <template #default="scope">
            <el-button size="small" type="primary" @click="handleApply(scope.row)">
              申请
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <div class="pagination-container">
        <el-pagination
          background
          layout="total, sizes, prev, pager, next, jumper"
          :total="pagination.total"
          :page-size="pagination.pageSize"
          :current-page="pagination.current"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>
    
    <!-- 申请对话框 -->
    <el-dialog
      title="教材申请"
      v-model="dialogVisible"
      width="500px"
    >
      <el-form
        ref="applyFormRef"
        :model="applyForm"
        :rules="applyRules"
        label-width="100px"
      >
        <el-form-item label="图书名称">
          <span>{{ currentBook?.bookName }}</span>
        </el-form-item>
        
        <el-form-item label="ISBN">
          <span>{{ currentBook?.isbn }}</span>
        </el-form-item>
        
        <el-form-item label="库存">
          <span>{{ currentBook?.inventory }}</span>
        </el-form-item>
        
        <el-form-item label="申请数量" prop="applyCount">
          <el-input-number v-model="applyForm.applyCount" :min="1" :max="currentBook?.inventory || 999" :step="1" />
        </el-form-item>
        
        <el-form-item label="申请理由" prop="applyReason">
          <el-input v-model="applyForm.applyReason" type="textarea" :rows="3" />
        </el-form-item>
      </el-form>
      
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitApply" :loading="submitLoading">
            提交申请
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useBookStore } from '@/stores/modules/book'
import { useApplyBookStore } from '@/stores/modules/applyBook'

export default {
  name: 'TeacherBookList',
  setup() {
    const bookStore = useBookStore()
    const applyBookStore = useApplyBookStore()
    
    const bookList = ref([])
    const loading = ref(false)
    const pagination = ref({
      total: 0,
      current: 1,
      pageSize: 10
    })
    const searchQuery = ref('')
    
    const dialogVisible = ref(false)
    const applyFormRef = ref(null)
    const submitLoading = ref(false)
    const currentBook = ref(null)
    
    const applyForm = reactive({
      applyCount: 1,
      applyReason: ''
    })
    
    const applyRules = {
      applyCount: [
        { required: true, message: '请输入申请数量', trigger: 'blur' },
        { type: 'number', min: 1, message: '申请数量必须大于0', trigger: 'blur' }
      ],
      applyReason: [
        { required: true, message: '请输入申请理由', trigger: 'blur' }
      ]
    }
    
    const fetchBookList = async () => {
      loading.value = true
      try {
        const response = await bookStore.fetchBookList(pagination.value.current, pagination.value.pageSize)
        bookList.value = response.list || []
        pagination.value = bookStore.pagination
      } catch (error) {
        ElMessage.error('获取图书列表失败')
      } finally {
        loading.value = false
      }
    }
    
    const handleApply = (row) => {
      currentBook.value = row
      applyForm.applyCount = 1
      applyForm.applyReason = ''
      dialogVisible.value = true
    }
    
    const submitApply = () => {
      applyFormRef.value.validate(async valid => {
        if (!valid) return
        
        submitLoading.value = true
        try {
          const applyData = {
            applyCount: applyForm.applyCount,
            applyReason: applyForm.applyReason
          }
          
          await applyBookStore.updateApplyCount(currentBook.value.bookId, applyData)
          ElMessage.success('申请成功')
          dialogVisible.value = false
        } catch (error) {
          ElMessage.error('申请失败')
        } finally {
          submitLoading.value = false
        }
      })
    }
    
    const handleSearch = () => {
      pagination.value.current = 1
      fetchBookList()
    }
    
    const handleSizeChange = (size) => {
      pagination.value.pageSize = size
      fetchBookList()
    }
    
    const handleCurrentChange = (page) => {
      pagination.value.current = page
      fetchBookList()
    }
    
    onMounted(() => {
      fetchBookList()
    })
    
    return {
      bookList,
      loading,
      pagination,
      searchQuery,
      dialogVisible,
      applyFormRef,
      applyForm,
      applyRules,
      submitLoading,
      currentBook,
      handleApply,
      submitApply,
      handleSearch,
      handleSizeChange,
      handleCurrentChange
    }
  }
}
</script>

<style scoped>
.teacher-book-list-container {
  padding: 20px;
}

.page-header {
  margin-bottom: 20px;
}

.search-bar {
  margin-bottom: 20px;
}

.pagination-container {
  margin-top: 20px;
  text-align: right;
}
</style>
