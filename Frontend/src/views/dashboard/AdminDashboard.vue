<template>
  <div class="dashboard-container">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover" class="dashboard-card">
          <template #header>
            <div class="card-header">
              <span>用户总数</span>
            </div>
          </template>
          <div class="card-body">
            <div class="card-value">{{ statistics.userCount }}</div>
            <div class="card-icon">
              <i class="el-icon-user"></i>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card shadow="hover" class="dashboard-card">
          <template #header>
            <div class="card-header">
              <span>教材总数</span>
            </div>
          </template>
          <div class="card-body">
            <div class="card-value">{{ statistics.bookCount }}</div>
            <div class="card-icon">
              <i class="el-icon-notebook-1"></i>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card shadow="hover" class="dashboard-card">
          <template #header>
            <div class="card-header">
              <span>待审批申请</span>
            </div>
          </template>
          <div class="card-body">
            <div class="card-value">{{ statistics.pendingApprovalCount }}</div>
            <div class="card-icon">
              <i class="el-icon-s-check"></i>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card shadow="hover" class="dashboard-card">
          <template #header>
            <div class="card-header">
              <span>待发放教材</span>
            </div>
          </template>
          <div class="card-body">
            <div class="card-value">{{ statistics.pendingDistributeCount }}</div>
            <div class="card-icon">
              <i class="el-icon-s-promotion"></i>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useApplyBookStore } from '@/stores/modules/applyBook'
import { useDistributeBookStore } from '@/stores/modules/distributeBook'
import { useUserStore } from '@/stores/modules/user'
import { useBookStore } from '@/stores/modules/book'

export default {
  name: 'AdminDashboard',
  setup() {
    const loading = ref(false)
    const statistics = ref({
      userCount: 0,
      bookCount: 0,
      pendingApprovalCount: 0,
      pendingDistributeCount: 0
    })
    
    const applyBookStore = useApplyBookStore()
    const distributeBookStore = useDistributeBookStore()
    const userStore = useUserStore()
    const bookStore = useBookStore()
    
    const fetchDashboardData = async () => {
      loading.value = true
      try {
        // 获取统计数据
        const [userCount, bookCount, pendingApprovalCount, pendingDistributeCount] = await Promise.all([
          userStore.getUserCount(),
          bookStore.getBookCount(),
          applyBookStore.getPendingApprovalCount(),
          distributeBookStore.getPendingDistributeCount()
        ])
        
        statistics.value = {
          userCount,
          bookCount,
          pendingApprovalCount,
          pendingDistributeCount
        }
      } catch (error) {
        ElMessage.error('获取仪表盘数据失败')
      } finally {
        loading.value = false
      }
    }
    
    onMounted(() => {
      fetchDashboardData()
    })
    
    return {
      loading,
      statistics
    }
  }
}
</script>

<style scoped>
.dashboard-container {
  padding: 24px;
}

.dashboard-card {
  height: 140px;
  border-radius: 8px;
  transition: all 0.3s;
  border: none;
  overflow: hidden;
}

.dashboard-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

:deep(.el-card__header) {
  padding: 16px 20px;
  border-bottom: 1px solid #f0f0f0;
  background-color: #fafafa;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 15px;
  color: #666;
  font-weight: 500;
}

.card-body {
  position: relative;
  height: 80px;
  padding: 20px;
  display: flex;
  align-items: center;
}

.card-value {
  font-size: 28px;
  font-weight: 600;
  color: #1890ff;
  line-height: 1.2;
}

.card-icon {
  position: absolute;
  right: 20px;
  bottom: 20px;
  font-size: 48px;
  color: #1890ff;
  opacity: 0.2;
  transition: all 0.3s;
}

.dashboard-card:hover .card-icon {
  transform: scale(1.1);
  opacity: 0.3;
}

:deep(.el-row) {
  margin-bottom: 24px;
}

:deep(.el-col) {
  margin-bottom: 24px;
}

@media screen and (max-width: 768px) {
  .dashboard-container {
    padding: 16px;
  }
  
  .dashboard-card {
    height: 120px;
  }
  
  .card-value {
    font-size: 24px;
  }
  
  .card-icon {
    font-size: 36px;
  }
}
</style> 