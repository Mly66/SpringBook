<template>
  <div class="storage-list-container">
    <div class="page-header">
      <h2>入库管理</h2>
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
        <el-table-column prop="approver" label="审批人" width="120">
          <template #default="scope">
            {{ scope.row.approverName || '未知' }}
          </template>
        </el-table-column>
        <el-table-column prop="approveDate" label="审批日期" width="120" />
        <el-table-column prop="applyStatusText" label="状态" width="120">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.applyStatus)">
              {{ scope.row.applyStatusText }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120">
          <template #default="scope">
            <el-button size="small" type="primary" @click="handleInStorage(scope.row)">
              入库
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
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useMenuStore } from '@/stores/modules/menu'
import { useApplyBookStore } from '@/stores/modules/applyBook'

export default {
  name: 'StorageList',
  setup() {
    const menuStore = useMenuStore()
    const applyBookStore = useApplyBookStore()
    
    const applyList = ref([])
    const loading = ref(false)
    const total = ref(0)
    const currentPage = ref(1)
    
    const fetchApplyList = async () => {
      loading.value = true
      try {
        const response = await menuStore.fetchMenuData(4, currentPage.value)
        applyList.value = response.list || []
        total.value = response.total || 0
      } catch (error) {
        ElMessage.error('获取入库列表失败')
      } finally {
        loading.value = false
      }
    }
    
    const getStatusType = (status) => {
      switch (status) {
        case 0: return 'info'    // 待审批
        case 1: return 'success' // 已通过
        case 2: return 'danger'  // 已拒绝
        case 3: return 'warning' // 已入库
        default: return 'info'
      }
    }
    
    const handleInStorage = async (row) => {
      try {
        await ElMessageBox.confirm(`确定要将 ${row.book.bookName} 入库吗？`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        
        await applyBookStore.inStorage(row.book.bookId)
        ElMessage.success('入库成功')
        fetchApplyList()
      } catch (error) {
        if (error !== 'cancel') {
          ElMessage.error('入库失败')
        }
      }
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
      getStatusType,
      handleInStorage,
      handleCurrentChange
    }
  }
}
</script>

<style scoped>
.storage-list-container {
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
