<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name"/>
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number v-model="teacher.sort" controls-position="right" :min="0"/>
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <!--
            数据类型一定要和取出的json中的一致，否则没法回填
            因此，这里value使用动态绑定的值，保证其数据类型是number
          -->
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career"/>
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea"/>
      </el-form-item>

      <!-- 讲师头像：TODO -->
      <el-form-item label="讲师头像">
          <!-- 头衔缩略图 -->
          <pan-thumb :image="teacher.avatar"/>
          <!-- 文件上传按钮 -->
          <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
          </el-button>
          <!--
              v-show：是否显示上传组件
              :key：类似于id，如果一个页面多个图片上传控件，可以做区分
              :url：后台上传的url地址
              @close：关闭上传组件
              @crop-upload-success：上传成功后的回调 -->
          <image-cropper
                        v-show="imagecropperShow"
                        :width="300"
                        :height="300"
                        :key="imagecropperKey"
                        :url="BASE_API+'/oss/file/upload'"
                        field="file"
                        @close="close"
                        @crop-upload-success="cropSuccess"/>
      </el-form-item> 

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import teacher from "@/api/edu/teacher"
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'


const defaultFrom = {
        name: '',
        sort: 0,
        level: 1,
        career: '',
        intro: '',
        avatar: 'https://tang-guli.oss-cn-beijing.aliyuncs.com/avatar/2020/10/07/9b5e82a0-3029-4a69-ad38-4c7b498aeb7e.jpg'
}
export default {
  components: { ImageCropper, PanThumb},
  data(){
    return{
      teacher: defaultFrom,
      saveBtnDisabled: false, //保存按钮是否禁用,保存按钮为亮色
      BASE_API: process.env.BASE_API, // 接口API地址
      imagecropperShow: false, // 是否显示上传组件
      imagecropperKey: 0 // 上传组件id
    }
  },
  watch: {
    $route(to,from){
      //当监听的路由这个方法执行的时候，
      //我们是不是判断此路由过来的参数是否存在，
      //如果不存在，那就说明是新增
      //如果存在那么说明修改过来的
      this.init()
    }
  },
  created(){  //在加载的时候执行了这个方法,可以调用这个根据id来查询的方法
    // 执行此方法,获取参数
    this.init()
  },
  methods:{
    init(){
      if(this.$route.params && this.$route.params.id){ //当加载页面的时候就要获取参数的值
        //修改路由
        this.selectById(this.$route.params.id)
      } else{
        //新增路由,深克隆
        this.teacher = {...defaultFrom}
      } 
    },
    saveOrUpdate(){
      this.saveBtnDisabled = true //禁用
      // 1、 怎么判断是否是新增还是修改
      // 2、 根据teacher.id来判断
      if(this.teacher.id){
        this.updateById()
      }else{
        this.save()
      }
    },
    save(){
      teacher.save(this.teacher)
        .then(response => {
          return this.$message({
            type: 'success',
            message: "保存成功"
          })
        })
        .then(response => {
          //保存成功后链接跳转
          this.$router.push({path : "/teacher/list"})
        })
        .catch(response => {
          return this.$message({
            type: 'error',
            message: '保存失败!'
          })
        })

    },
    updateById(){
        teacher.updateById(this.teacher)
          .then(response => {
            //修改提示
            this.$message({
              type:'success',
              message:'修改成功'
            })
          })
          .then(response => {
            //页面跳转
            this.$router.push({path:"/teacher/list"})
          })
          .catch(response => {
            this.$message({
              type:'error',
              message:response.data.message
            })
          })
    },
    selectById(id){
        teacher.selectById(id)
        .then(response => {
          this.teacher = response.data.teacher;
        }).catch( response => {
          this.$message({
            type:'error',
            message:'获取数据失败!'
          })
        })
    },
    close(){
      //1. 关闭上传图片框
      this.imagecropperShow = false, //是否显示上传组件
      //2. 给框ID变化一次
      this.imagecropperKey = this.imagecropperKey + 1
    },
    //保存图片成功
    cropSuccess(data){
      this.teacher.avatar = data.url
      //1. 关闭上传图片框
      this.imagecropperShow = false, //是否显示上传组件
      //2. 给框ID变化一次
      this.imagecropperKey = this.imagecropperKey + 1
    }

  },

}
</script>