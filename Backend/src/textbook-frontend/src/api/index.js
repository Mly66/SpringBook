import request from '@/utils/request'

// 导出所有API
import * as userApi from './user'
import * as bookApi from './book'
import * as applyBookApi from './applyBook'
import * as courseApi from './course'
import * as publisherApi from './publisher'
import * as distributeBookApi from './distributeBook'
import * as menuApi from './menu'

export default {
  user: userApi,
  book: bookApi,
  applyBook: applyBookApi,
  course: courseApi,
  publisher: publisherApi,
  distributeBook: distributeBookApi,
  menu: menuApi
}
