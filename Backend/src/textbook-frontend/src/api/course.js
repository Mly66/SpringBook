import request from '@/utils/request'

// 保存课程信息（新增或更新）
export function saveCourse(data) {
  return request({
    url: '/course/save',
    method: 'post',
    data
  })
}

// 获取课程详情
export function getCourse(courseId) {
  return request({
    url: `/course/${courseId}`,
    method: 'get'
  })
}

// 删除课程
export function deleteCourse(courseId) {
  return request({
    url: `/course/${courseId}`,
    method: 'delete'
  })
}
