<template>
  <div class="dashboard-container">
    <div class="page-header">
      <h2>教师仪表盘</h2>
    </div>
    
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card class="box-card">
          <div class="card-header">
            <i class="el-icon-s-order"></i>
            <span>我的申请</span>
          </div>
          <div class="card-body">
            <div class="card-value">{{ dashboardData.myApplyCount || 0 }}</div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="8">
        <el-card class="box-card">
          <div class="card-header">
            <i class="el-icon-s-promotion"></i>
            <span>已领取教材</span>
          </div>
          <div class="card-body">
            <div class="card-value">{{ dashboardData.myDistributeCount || 0 }}</div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="8">
        <el-card class="box-card">
          <div class="card-header">
            <i class="el-icon-notebook-1"></i>
            <span>可申请图书</span>
          </div>
          <div class="card-body">
            <div class="card-value">{{ dashboardData.availableBookCount || 0 }}</div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>我的申请记录</span>
          </div>
          <el-table :data="dashboardData.myApplies || []" style="width: 100%">
            <el-table-column prop="book.bookName" label="图书名称" width="180" />
            <el-table-column prop="applyDate" label="申请日期" width="120" />
            <el-table-column prop="applyCount" label="申请数量" width="100" />
            <el-table-column prop="applyStatusText" label="状态" width="100">
              <template #default="scope">
                <el-tag :type="getStatusType(scope.row.applyStatus)">
                  {{ scope.row.applyStatusText }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="approveDate" label="审批日期" width="120" />
            <el-table-column prop="distributeDate" label="发放日期" width="120" />
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useMenuStore } from '@/stores/modules/menu'

export default {
  name: 'TeacherDashboard',
  setup() {
    const menuStore = useMenuStore()
    const dashboardData = ref({})
    const loading = ref(false)
    
    const fetchDashboardData = async () => {
      loading.value = true
      try {
        const response = await menuStore.fetchMenuData(11)
        dashboardData.value = response || {}
      } catch (error) {
        ElMessage.error('获取仪表盘数据失败')
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
    
    onMounted(() => {
      fetchDashboardData()
    })
    
    return {
      dashboardData,
      loading,
      getStatusType
    }
  }
}
</script>

<style scoped>
.dashboard-container {
  padding: 20px;
}

.page-header {
  margin-bottom: 20px;
}

.box-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  align-items: center;
  font-size: 16px;
  margin-bottom: 10px;
}

.card-header i {
  margin-right: 8px;
  font-size: 20px;
}

.card-body {
  text-align: center;
}

.card-value {
  font-size: 28px;
  font-weight: bold;
  color: #409EFF;
}
</style>
