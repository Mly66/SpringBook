<template>
  <div class="course-list-container">
    <div class="page-header">
      <h2>课程管理</h2>
      <el-button type="primary" @click="handleAddCourse">添加课程</el-button>
    </div>
    
    <el-card class="box-card">
      <el-table
        :data="courseList"
        border
        style="width: 100%"
        v-loading="loading"
      >
        <el-table-column prop="courseId" label="ID" width="80" />
        <el-table-column prop="courseName" label="课程名称" width="250" />
        <el-table-column prop="period" label="学时" width="120" />
        <el-table-column prop="credit" label="学分" width="120" />
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
import { useCourseStore } from '@/stores/modules/course'

export default {
  name: 'CourseList',
  setup() {
    const router = useRouter()
    const menuStore = useMenuStore()
    const courseStore = useCourseStore()
    
    const courseList = ref([])
    const loading = ref(false)
    const total = ref(0)
    const currentPage = ref(1)
    
    const fetchCourseList = async () => {
      loading.value = true
      try {
        const response = await menuStore.fetchMenuData(2, currentPage.value)
        courseList.value = response.list || []
        total.value = response.total || 0
      } catch (error) {
        ElMessage.error('获取课程列表失败')
      } finally {
        loading.value = false
      }
    }
    
    const handleAddCourse = () => {
      router.push('/admin/courses/add')
    }
    
    const handleEdit = (row) => {
      router.push(`/admin/courses/edit/${row.courseId}`)
    }
    
    const handleDelete = async (row) => {
      try {
        await ElMessageBox.confirm(`确定要删除课程 ${row.courseName} 吗？`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        
        await courseStore.removeCourse(row.courseId)
        ElMessage.success('删除成功')
        fetchCourseList()
      } catch (error) {
        if (error !== 'cancel') {
          ElMessage.error('删除失败')
        }
      }
    }
    
    const handleCurrentChange = (page) => {
      currentPage.value = page
      fetchCourseList()
    }
    
    onMounted(() => {
      fetchCourseList()
    })
    
    return {
      courseList,
      loading,
      total,
      currentPage,
      handleAddCourse,
      handleEdit,
      handleDelete,
      handleCurrentChange
    }
  }
}
</script>

<style scoped>
.course-list-container {
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
