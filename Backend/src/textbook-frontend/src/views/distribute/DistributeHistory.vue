<template>
  <div class="distribute-history-container">
    <div class="page-header">
      <h2>发放历史</h2>
    </div>
    
    <el-card class="box-card">
      <el-table
        :data="distributeList"
        border
        style="width: 100%"
        v-loading="loading"
      >
        <el-table-column prop="book.bookName" label="图书名称" width="200" />
        <el-table-column prop="user.userName" label="领取人" width="120" />
        <el-table-column prop="distributeDate" label="发放日期" width="120" />
        <el-table-column prop="distributeCount" label="发放数量" width="100" />
        <el-table-column prop="distributor" label="发放人" width="120" />
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
import { useDistributeBookStore } from '@/stores/modules/distributeBook'

export default {
  name: 'DistributeHistory',
  setup() {
    const menuStore = useMenuStore()
    const distributeBookStore = useDistributeBookStore()
    
    const distributeList = ref([])
    const loading = ref(false)
    const total = ref(0)
    const currentPage = ref(1)
    
    const fetchDistributeHistory = async () => {
      loading.value = true
      try {
        const response = await menuStore.fetchMenuData(7, currentPage.value)
        distributeList.value = response.list || []
        total.value = response.total || 0
      } catch (error) {
        ElMessage.error('获取发放历史失败')
      } finally {
        loading.value = false
      }
    }
    
    const handleCurrentChange = (page) => {
      currentPage.value = page
      fetchDistributeHistory()
    }
    
    onMounted(() => {
      fetchDistributeHistory()
    })
    
    return {
      distributeList,
      loading,
      total,
      currentPage,
      handleCurrentChange
    }
  }
}
</script>

<style scoped>
.distribute-history-container {
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
