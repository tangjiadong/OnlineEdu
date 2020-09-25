<template>
  <div class="app-container">
    讲师列表
  </div>
</template>

<script>
//引用teacher.js
import teacher from "@/api/edu/teacher"

export default {
  //定义数据变量
  data(){
    return {}
  },
  //当页面加载时获取数据
  created(){
    //调用查询列表的方法
    this.getPageList()
  },
  //存放方法
  methods:{
    getPageList(){
      //发送请求从后台获取数据

    }
  }


}
</script>
