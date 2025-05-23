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
          <template #header>
            <span>我的申请记录</span>
          </template>
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
import { useApplyBookStore } from '@/stores/modules/applyBook'
import { useBookStore } from '@/stores/modules/book'

export default {
  name: 'TeacherDashboard',
  setup() {
    const applyBookStore = useApplyBookStore()
    const bookStore = useBookStore()
    const dashboardData = ref({
      myApplyCount: 0,
      myDistributeCount: 0,
      availableBookCount: 0,
      myApplies: []
    })
    const loading = ref(false)

    const fetchDashboardData = async () => {
      loading.value = true
      try {
        // 获取用户申请列表
        const list = await applyBookStore.fetchUserApplyList()
        dashboardData.value.myApplyCount = list.length
        // 修改为统计已发放（状态为4）的记录
        dashboardData.value.myDistributeCount = list.filter(item => item.applyStatus === 4).length
        // 获取可申请图书数量
        const availableBooks = await bookStore.fetchBookList(1, 1)
        dashboardData.value.availableBookCount = availableBooks.total || 0
        dashboardData.value.myApplies = list.map(item => ({
          ...item,
          applyStatusText: applyBookStore.getApplyStatusText(item.applyStatus)
        }))
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
