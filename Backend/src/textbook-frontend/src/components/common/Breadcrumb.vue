<template>
  <div class="breadcrumb-container">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item v-for="(item, index) in breadcrumbs" :key="index" :to="item.path">
        {{ item.title }}
      </el-breadcrumb-item>
    </el-breadcrumb>
  </div>
</template>

<script>
import { ref, watch } from 'vue'
import { useRoute } from 'vue-router'

export default {
  name: 'Breadcrumb',
  setup() {
    const route = useRoute()
    const breadcrumbs = ref([])
    
    const getBreadcrumbs = () => {
      const matched = route.matched.filter(item => item.meta && item.meta.title)
      
      const first = matched[0]
      if (first && first.path !== '/') {
        breadcrumbs.value = [
          { path: '/', title: '首页' },
          ...matched.map(item => ({
            path: item.path,
            title: item.meta.title
          }))
        ]
      } else {
        breadcrumbs.value = matched.map(item => ({
          path: item.path,
          title: item.meta.title
        }))
      }
    }
    
    watch(() => route.path, getBreadcrumbs, { immediate: true })
    
    return {
      breadcrumbs
    }
  }
}
</script>

<style scoped>
.breadcrumb-container {
  padding: 0 10px;
}
</style>
