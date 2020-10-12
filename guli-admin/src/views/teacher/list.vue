<template>
  <div class="app-container">
    <!-- 查询表单 -->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="searchObj.name" placeholder="讲师名"/>
      </el-form-item>

      <el-form-item>
        <el-select v-model="searchObj.level" clearable placeholder="讲师头衔">
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>

      <el-form-item label="添加时间">
        <!-- element-ui的 date-picker组件默认绑定的时间值是默认世界标准时间，和中国时间差8小时 -->
        <!-- 设置 value-format="yyyy-MM-dd HH:mm:ss" 改变绑定的值 -->
        <el-date-picker
          v-model="searchObj.begin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="searchObj.end"
          type="datetime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getPageList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 表格 -->
      <el-table
        :data="list"
        border
        fit
        v-loading="listLoading"
        element-loading-text="数据加载中"
        highlight-current-row>

        <el-table-column
          label="序号"
          width="70"
          align="center">
          <template slot-scope="scope">
            {{ (page - 1) * limit + scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column
          prop="name"
          label="名称"
          width="80">
        </el-table-column>
        <el-table-column
          label="头衔"
          width="80">
          <template slot-scope="scope">
            {{ scope.row.level===1?'高级讲师':'首席讲师'}}
          </template>
        </el-table-column>
        <el-table-column prop="intro" label="资历" />
        <el-table-column prop="gmtCreate" label="添加时间" width="160"/>
        <el-table-column prop="sort" label="排序" width="60"/>
        <el-table-column label="操作" width="200" align="center">
          <template slot-scope="scope">
            <router-link :to="'/teacher/edit/'+scope.row.id">
              <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
            </router-link>
            <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <!-- @current-change	： 当页码发生点击的时候触发事件，调用方法 -->
      <el-pagination
        :current-page="page"
        :page-size="limit"
        :total="total"
        style="padding: 30px 0; text-align: center;"
        layout="total, prev, pager, next, jumper"
        @current-change="getPageList"
      />

  </div>
</template>

<script>
//引用teacher.js
import teacher from "@/api/edu/teacher"

export default {
  //定义数据变量
  data(){
    return {
      listLoading: true, //是否显示loading信息
      list: null, //数据列表
      total: 0, //总记录数
      page: 1, //页码
      limit: 10, //每页记录数
      searchObj: {}//查询条件
    }
  },
  //当页面加载时获取数据
  created(){
    //调用查询列表的方法
    this.getPageList()
  },
  //存放方法
  methods:{
    getPageList(page = 1){
      this.page = page
      //发送请求从后台获取数据
      teacher.getPageList(this.page,this.limit,this.searchObj)
        .then(response => {
          console.log(response)
          console.log(response.data)
          this.list = response.data.rows
          this.total = response.data.total
          //当数据获取完了后吧loading关闭
          this.listLoading = false
        })
        .catch(response => {
          console.log(response.data.message)
        })
    },
    // 清空查询条件
    resetData(){
      this.searchObj = {}
      this.getPageList()
    },
    //删除讲师
    removeDataById(id){
      //提示:是否删除
      this.$confirm('此操作将永久删除该记录,是否继续?','提示',{
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        return teacher.removeDataById(id)
      }).then(() => {
        //已经向后台发送请求并且是正确的了
        this.$message({
          type:'success',
          message:"删除成功"
        })
        //刷新页面
        this.getPageList()
      }).catch((response) =>{ //不管点击取消还是删除失败都走这个catch
        if(response === 'cancel'){
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        } else {
          this.$message({
            type: 'error',
            message: '删除失败'
          })
        }

      })
    }

  }
}
</script>
