<template>
  <div class="distribute-list-container">
    <div class="page-header">
      <h2>教材发放</h2>
    </div>
    
    <el-card class="box-card">
      <el-table
        :data="applyList"
        border
        style="width: 100%"
        v-loading="loading"
      >
        <el-table-column prop="book.bookName" label="图书名称" width="200" />
        <el-table-column prop="user.userName" label="申请人" width="120" />
        <el-table-column prop="applyDate" label="申请日期" width="120" />
        <el-table-column prop="applyCount" label="申请数量" width="100" />
        <el-table-column prop="applyStatusText" label="状态" width="120">
          <template #default="scope">
            <el-tag type="warning">{{ scope.row.applyStatusText }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button size="small" type="primary" @click="handleDistribute(scope.row)">
              发放
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <div class="pagination-container">
        <el-pagination
          background
          layout="total, prev, pager, next"
          :total="total"
          :current-page="currentPage"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>
    
    <!-- 发放对话框 -->
    <el-dialog
      title="教材发放"
      v-model="dialogVisible"
      width="500px"
    >
      <el-form
        ref="distributeFormRef"
        :model="distributeForm"
        :rules="distributeRules"
        label-width="100px"
      >
        <el-form-item label="图书名称">
          <span>{{ currentApply?.book?.bookName }}</span>
        </el-form-item>
        
        <el-form-item label="申请人">
          <span>{{ currentApply?.user?.userName }}</span>
        </el-form-item>
        
        <el-form-item label="申请数量">
          <span>{{ currentApply?.applyCount }}</span>
        </el-form-item>
        
        <el-form-item label="发放数量" prop="distributeCount">
          <el-input-number v-model="distributeForm.distributeCount" :min="1" :step="1" />
        </el-form-item>
      </el-form>
      
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitDistribute" :loading="submitLoading">
            确认发放
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useMenuStore } from '@/stores/modules/menu'
import { useDistributeBookStore } from '@/stores/modules/distributeBook'

export default {
  name: 'DistributeList',
  setup() {
    const menuStore = useMenuStore()
    const distributeBookStore = useDistributeBookStore()
    
    const applyList = ref([])
    const loading = ref(false)
    const total = ref(0)
    const currentPage = ref(1)
    
    const dialogVisible = ref(false)
    const distributeFormRef = ref(null)
    const submitLoading = ref(false)
    const currentApply = ref(null)
    
    const distributeForm = reactive({
      distributeCount: 0
    })
    
    const distributeRules = {
      distributeCount: [
        { required: true, message: '请输入发放数量', trigger: 'blur' },
        { type: 'number', min: 1, message: '发放数量必须大于0', trigger: 'blur' }
      ]
    }
    
    const fetchApplyList = async () => {
      loading.value = true
      try {
        const response = await menuStore.fetchMenuData(5, currentPage.value)
        applyList.value = response.list || []
        total.value = response.total || 0
      } catch (error) {
        ElMessage.error('获取发放列表失败')
      } finally {
        loading.value = false
      }
    }
    
    const handleDistribute = (row) => {
      currentApply.value = row
      distributeForm.distributeCount = row.applyCount
      dialogVisible.value = true
    }
    
    const submitDistribute = () => {
      distributeFormRef.value.validate(async valid => {
        if (!valid) return
        
        submitLoading.value = true
        try {
          const data = {
            bookId: currentApply.value.book.bookId,
            userId: currentApply.value.user.userId,
            distributeCount: distributeForm.distributeCount
          }
          
          await distributeBookStore.distributeFromApply(data)
          ElMessage.success('发放成功')
          dialogVisible.value = false
          fetchApplyList()
        } catch (error) {
          ElMessage.error('发放失败')
        } finally {
          submitLoading.value = false
        }
      })
    }
    
    const handleCurrentChange = (page) => {
      currentPage.value = page
      fetchApplyList()
    }
    
    onMounted(() => {
      fetchApplyList()
    })
    
    return {
      applyList,
      loading,
      total,
      currentPage,
      dialogVisible,
      distributeFormRef,
      distributeForm,
      distributeRules,
      submitLoading,
      currentApply,
      handleDistribute,
      submitDistribute,
      handleCurrentChange
    }
  }
}
</script>

<style scoped>
.distribute-list-container {
  padding: 20px;
}

.page-header {
  margin-bottom: 20px;
}

.pagination-container {
  margin-top: 20px;
  text-align: right;
}
</style>
