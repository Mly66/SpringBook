<template>
  <div class="book-list-container">
    <div class="page-header">
      <h2>图书管理</h2>
      <el-button type="primary" @click="handleAddBook">添加图书</el-button>
    </div>
    
    <el-card class="box-card">
      <div class="search-bar">
        <el-input
          v-model="searchQuery"
          placeholder="搜索图书名称或ISBN"
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
        :data="bookList"
        border
        style="width: 100%"
        v-loading="loading"
      >
        <el-table-column prop="bookId" label="ID" width="80" />
        <el-table-column prop="bookName" label="图书名称" width="200" />
        <el-table-column prop="isbn" label="ISBN" width="150" />
        <el-table-column prop="author" label="作者" width="120" />
        <el-table-column prop="price" label="价格" width="100">
          <template #default="scope">
            {{ scope.row.price }} 元
          </template>
        </el-table-column>
        <el-table-column prop="pubDate" label="出版日期" width="120" />
        <el-table-column prop="inventory" label="库存" width="80" />
        <el-table-column label="操作" width="250">
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
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
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useBookStore } from '@/stores/modules/book'

export default {
  name: 'BookList',
  setup() {
    const router = useRouter()
    const bookStore = useBookStore()
    
    const bookList = ref([])
    const loading = ref(false)
    const pagination = ref({
      total: 0,
      current: 1,
      pageSize: 10
    })
    const searchQuery = ref('')
    
    const fetchBookList = async () => {
      loading.value = true
      try {
        const response = await bookStore.fetchBookList(pagination.value.current, pagination.value.pageSize)
        bookList.value = response.list || []
        pagination.value = bookStore.pagination
      } catch (error) {
        ElMessage.error('获取图书列表失败')
      } finally {
        loading.value = false
      }
    }
    
    const handleAddBook = () => {
      router.push('/admin/books/add')
    }
    
    const handleEdit = (row) => {
      router.push(`/admin/books/edit/${row.bookId}`)
    }
    
    const handleDelete = async (row) => {
      try {
        await ElMessageBox.confirm(`确定要删除图书 ${row.bookName} 吗？`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        
        await bookStore.removeBook(row.bookId)
        ElMessage.success('删除成功')
        fetchBookList()
      } catch (error) {
        if (error !== 'cancel') {
          ElMessage.error('删除失败')
        }
      }
    }
    
    const handleSearch = () => {
      pagination.value.current = 1
      fetchBookList()
    }
    
    const handleSizeChange = (size) => {
      pagination.value.pageSize = size
      fetchBookList()
    }
    
    const handleCurrentChange = (page) => {
      pagination.value.current = page
      fetchBookList()
    }
    
    onMounted(() => {
      fetchBookList()
    })
    
    return {
      bookList,
      loading,
      pagination,
      searchQuery,
      handleAddBook,
      handleEdit,
      handleDelete,
      handleSearch,
      handleSizeChange,
      handleCurrentChange
    }
  }
}
</script>

<style scoped>
.book-list-container {
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
