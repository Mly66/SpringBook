<template>
  <div class="apply-list-container">
    <div class="page-header">
      <h2>我的申请</h2>
    </div>
    
    <el-card class="box-card">
      <el-table
        :data="applyList"
        border
        style="width: 100%"
        v-loading="loading"
      >
        <el-table-column prop="book.bookName" label="图书名称" width="200" />
        <el-table-column prop="applyDate" label="申请日期" width="120" />
        <el-table-column prop="applyCount" label="申请数量" width="100" />
        <el-table-column prop="applyReason" label="申请理由" width="200" />
        <el-table-column prop="applyStatusText" label="状态" width="120">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.applyStatus)">
              {{ scope.row.applyStatusText }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="approver" label="审批人" width="120">
          <template #default="scope">
            {{ scope.row.approverName || '未审批' }}
          </template>
        </el-table-column>
        <el-table-column prop="approveDate" label="审批日期" width="120" />
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
import { ElMessage } from 'element-plus'
import { useMenuStore } from '@/stores/modules/menu'

export default {
  name: 'ApplyList',
  setup() {
    const menuStore = useMenuStore()
    
    const applyList = ref([])
    const loading = ref(false)
    const total = ref(0)
    const currentPage = ref(1)
    
    const fetchApplyList = async () => {
      loading.value = true
      try {
        const response = await menuStore.fetchMenuData(9, currentPage.value)
        applyList.value = response.list || []
        total.value = response.total || 0
      } catch (error) {
        ElMessage.error('获取申请列表失败')
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
        case 4: return 'success' // 已发放
        default: return 'info'
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
      handleCurrentChange
    }
  }
}
</script>

<style scoped>
.apply-list-container {
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
