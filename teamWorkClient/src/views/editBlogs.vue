<template>
  <div>
    <div class="m-content">
      <h2>编写你的博客吧！</h2>

      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="标题" prop="title">
          <el-input v-model="ruleForm.title"></el-input>
        </el-form-item>

        <el-form-item label="摘要" prop="description">
          <el-input type="textarea" v-model="ruleForm.description"></el-input>
        </el-form-item>

        <el-form-item label="内容" prop="content">
          <mavon-editor v-model="ruleForm.content"></mavon-editor>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')" v-if="ifNew">立即创建</el-button>
          <el-button type="primary" @click="submitForm('ruleForm')" v-if="!ifNew">更新</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>

    </div>



  </div>
</template>

<script>
import request from "../util/request";

export default {
  data() {
    return {
      ifNew:true,
      data:{},
      user:{},
      ruleForm: {
        id: '',
        userId:'',
        title: '',
        description: '',
        content: ''
      },
      rules: {
        title: [
          { required: true, message: '请输入标题', trigger: 'blur' },
          { min: 3, max: 25, message: '长度在 3 到 25 个字符', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '请输入摘要', trigger: 'blur' }
        ],
        content: [
          { trequired: true, message: '请输入内容', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log(this.ruleForm)
          request.post('http://localhost:8081/blog/edit', this.ruleForm).then(res => {
            console.log(res)
            this.$alert('操作成功', '提示', {
              confirmButtonText: '确定',
              callback: action => {
                this.$router.go(-1);
              }
            });

          })

        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  },
  created() {
    this.data = JSON.parse(decodeURIComponent(this.$route.params.data))
    let blogId = this.data.blogId
    this.user = this.data.user
    this.ruleForm.userId = this.user.id
    console.log(this.ruleForm.userId)
    if(blogId){
      this.ifNew = false;
    }
    if(!this.ruleForm.userId){
      this.$message({
        type:"warning",
        message:"请先登录",
      })
    }else{
      let url = 'http://localhost:8081/blog/'+blogId
      request.get(url).then(res=>{
        const blog = res.data
        this.ruleForm.id = blog.id
        this.ruleForm.title = blog.title
        this.ruleForm.description = blog.description
        this.ruleForm.content = blog.content
      })
    }

  }
}
</script>

<style scoped>
.m-content {
  text-align: center;
  margin: 80px;
}
</style>
