<template>
  <div class="publisher-list-container">
    <div class="page-header">
      <h2>出版社管理</h2>
      <el-button type="primary" @click="handleAddPublisher">添加出版社</el-button>
    </div>
    
    <el-card class="box-card">
      <el-table
        :data="publisherList"
        border
        style="width: 100%"
        v-loading="loading"
      >
        <el-table-column prop="pubId" label="ID" width="80" />
        <el-table-column prop="pubName" label="出版社名称" width="250" />
        <el-table-column prop="contacter" label="联系人" width="150" />
        <el-table-column prop="mobile" label="联系电话" width="150" />
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
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
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useMenuStore } from '@/stores/modules/menu'
import { usePublisherStore } from '@/stores/modules/publisher'

export default {
  name: 'PublisherList',
  setup() {
    const router = useRouter()
    const menuStore = useMenuStore()
    const publisherStore = usePublisherStore()
    
    const publisherList = ref([])
    const loading = ref(false)
    const total = ref(0)
    const currentPage = ref(1)
    
    const fetchPublisherList = async () => {
      loading.value = true
      try {
        const response = await menuStore.fetchMenuData(8, currentPage.value)
        publisherList.value = response.list || []
        total.value = response.total || 0
      } catch (error) {
        ElMessage.error('获取出版社列表失败')
      } finally {
        loading.value = false
      }
    }
    
    const handleAddPublisher = () => {
      router.push('/admin/publishers/add')
    }
    
    const handleEdit = (row) => {
      router.push(`/admin/publishers/edit/${row.pubId}`)
    }
    
    const handleDelete = async (row) => {
      try {
        await ElMessageBox.confirm(`确定要删除出版社 ${row.pubName} 吗？`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        
        await publisherStore.removePublisher(row.pubId)
        ElMessage.success('删除成功')
        fetchPublisherList()
      } catch (error) {
        if (error !== 'cancel') {
          ElMessage.error('删除失败')
        }
      }
    }
    
    const handleCurrentChange = (page) => {
      currentPage.value = page
      fetchPublisherList()
    }
    
    onMounted(() => {
      fetchPublisherList()
    })
    
    return {
      publisherList,
      loading,
      total,
      currentPage,
      handleAddPublisher,
      handleEdit,
      handleDelete,
      handleCurrentChange
    }
  }
}
</script>

<style scoped>
.publisher-list-container {
  padding: 20px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.pagination-container {
  margin-top: 20px;
  text-align: right;
}
</style>
