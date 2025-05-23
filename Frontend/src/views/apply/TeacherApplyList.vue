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
        <el-table-column prop="book.isbn" label="ISBN" width="150" />
        <el-table-column prop="applyDate" label="申请日期" width="180" />
        <el-table-column prop="applyCount" label="申请数量" width="100" />
        <el-table-column prop="applyStatusText" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.applyStatus)">
              {{ scope.row.applyStatusText }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="approveDate" label="审批日期" width="180" />
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
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useApplyBookStore } from '@/stores/modules/applyBook'

export default {
  name: 'TeacherApplyList',
  setup() {
    const applyBookStore = useApplyBookStore()
    
    const applyList = ref([])
    const loading = ref(false)
    const pagination = ref({
      total: 0,
      current: 1,
      pageSize: 10
    })
    
    const getStatusType = (status) => {
      switch (status) {
        case 0: return 'info'    // 待审批
        case 1: return 'success' // 已通过
        case 2: return 'danger'  // 已拒绝
        case 3: return 'warning' // 已入库
        default: return 'info'
      }
    }
    
    const fetchApplyList = async () => {
      loading.value = true
      try {
        const response = await applyBookStore.fetchUserApplyList()
        applyList.value = response || []
        // 由于用户申请列表不分页，设置总数为列表长度
        pagination.value.total = applyList.value.length
      } catch (error) {
        console.error('获取申请列表失败:', error)
        ElMessage.error(error.response?.data?.message || '获取申请列表失败')
      } finally {
        loading.value = false
      }
    }
    
    const handleSizeChange = (size) => {
      pagination.value.pageSize = size
      fetchApplyList()
    }
    
    const handleCurrentChange = (page) => {
      pagination.value.current = page
      fetchApplyList()
    }
    
    onMounted(() => {
      fetchApplyList()
    })
    
    return {
      applyList,
      loading,
      pagination,
      getStatusType,
      handleSizeChange,
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