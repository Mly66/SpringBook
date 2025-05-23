<template>
  <div class="app-footer">
    <span>© {{ currentYear }} 教材管理系统 - 版权所有</span>
  </div>
</template>

<script>
import { computed } from 'vue'

export default {
  name: 'AppFooter',
  setup() {
    const currentYear = computed(() => new Date().getFullYear())
    
    return {
      currentYear
    }
  }
}
</script>

<style scoped>
.app-footer {
  text-align: center;
  font-size: 14px;
  color: #666;
}
</style>
