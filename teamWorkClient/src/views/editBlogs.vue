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
          <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
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
      ruleForm: {
        id: '',
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

    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  },
  created() {
    let blogId = this.$route.query.blogId
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
</script>

<style scoped>
.m-content {
  text-align: center;
  margin: 80px;
}
</style>
