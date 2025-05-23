<template>
  <div class="user-list-container">
    <div class="page-header">
      <h2>用户管理</h2>
      <el-button type="primary" @click="handleAddUser">添加用户</el-button>
    </div>
    
    <el-card class="box-card">
      <div class="search-bar">
        <el-input
          v-model="searchQuery"
          placeholder="搜索用户名或姓名"
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
        :data="userList"
        border
        style="width: 100%"
        v-loading="loading"
      >
        <el-table-column prop="userId" label="ID" width="80" />
        <el-table-column prop="loginName" label="用户名" width="150" />
        <el-table-column prop="userName" label="姓名" width="150" />
        <el-table-column prop="mobile" label="手机号" width="150" />
        <el-table-column prop="roles" label="角色" width="200">
          <template #default="scope">
            <el-tag v-for="role in scope.row.roles" :key="role.roleId" style="margin-right: 5px">
              {{ role.roleName }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="250">
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="small" type="warning" @click="handleResetPassword(scope.row)">重置密码</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <div class="pagination-container">
        <el-pagination
          background
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          :page-size="pageSize"
          :current-page="currentPage"
          @size-change="handleSizeChange"
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
import { useUserStore } from '@/stores/modules/user'

export default {
  name: 'UserList',
  setup() {
    const router = useRouter()
    const menuStore = useMenuStore()
    const userStore = useUserStore()
    
    const userList = ref([])
    const loading = ref(false)
    const total = ref(0)
    const currentPage = ref(1)
    const pageSize = ref(10)
    const searchQuery = ref('')
    
    const fetchUserList = async () => {
      loading.value = true
      try {
        const response = await menuStore.fetchMenuData(1, currentPage.value, pageSize.value)
        userList.value = response.list || []
        total.value = response.total || 0
      } catch (error) {
        ElMessage.error('获取用户列表失败')
      } finally {
        loading.value = false
      }
    }
    
    const handleAddUser = () => {
      router.push('/admin/users/add')
    }
    
    const handleEdit = (row) => {
      router.push(`/admin/users/edit/${row.userId}`)
    }
    
    const handleResetPassword = async (row) => {
      try {
        await ElMessageBox.confirm(`确定要重置用户 ${row.userName} 的密码吗？`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        
        await userStore.resetUserPassword(row.userId)
        ElMessage.success('密码重置成功')
      } catch (error) {
        if (error !== 'cancel') {
          ElMessage.error('密码重置失败')
        }
      }
    }
    
    const handleDelete = async (row) => {
      try {
        await ElMessageBox.confirm(`确定要删除用户 ${row.userName} 吗？`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        
        await userStore.removeUser(row.userId)
        ElMessage.success('删除成功')
        fetchUserList()
      } catch (error) {
        if (error !== 'cancel') {
          ElMessage.error('删除失败')
        }
      }
    }
    
    const handleSearch = () => {
      currentPage.value = 1
      fetchUserList()
    }
    
    const handleSizeChange = (size) => {
      pageSize.value = size
      fetchUserList()
    }
    
    const handleCurrentChange = (page) => {
      currentPage.value = page
      fetchUserList()
    }
    
    onMounted(() => {
      fetchUserList()
    })
    
    return {
      userList,
      loading,
      total,
      currentPage,
      pageSize,
      searchQuery,
      handleAddUser,
      handleEdit,
      handleResetPassword,
      handleDelete,
      handleSearch,
      handleSizeChange,
      handleCurrentChange
    }
  }
}
</script>

<style scoped>
.user-list-container {
  padding: 20px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
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
