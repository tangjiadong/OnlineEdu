import request from '@/utils/request'

//引入方法: import teacher from "@/api/edu/teacher"
//teacher.getList()
export function getList() {
  return request({
    url: '/teacher/list',
    method: 'get'
  })
}
