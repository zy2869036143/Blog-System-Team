<template>
  <div>
    <div v-if="ifLogin" class="m-content">
      <h2>编写你的博客吧！</h2>

      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="标题" prop="title">
          <el-input v-model="ruleForm.title"></el-input>
        </el-form-item>

        <el-form-item label="摘要" prop="description">
          <el-input type="textarea" v-model="ruleForm.description"></el-input>
        </el-form-item>
        <el-form-item label="博客标签分类" prop="category">
        <div style="margin-bottom: 10px">
          <el-tooltip v-for="(tag,index) in tags" :key="index" :content="tag.id" placement="top" effect="dark">
            <el-button v-if="!tag.ifChosen" :icon="tag.icon" circle @click="chooseTag($event,index)"></el-button>
            <el-button v-if="tag.ifChosen" type="warning" plain :icon="tag.icon" circle @click="chooseTag($event,index)"></el-button>
          </el-tooltip>

        </div>
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

    <el-empty v-if="!ifLogin" description="请先登录哦~"></el-empty>

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
      tagChoice:[],
      tags:[
        {ifChosen:false,
        id:"python",
        icon:"el-icon-python"
      },
        {
          id:"c/c++",
          icon:"el-icon-cyuyan",
          ifChosen:false
        },
        {
          id:"java",
          icon:"el-icon-java",
          ifChosen:false
        },
        {
          id:"dart",
          icon:"el-icon-dart",
          ifChosen:false
        },
        {ifChosen:false,
          id:"javaScript",
          icon:"el-icon-js"
        },
        {ifChosen:false,
          id:"swift",
          icon:"el-icon-swift"
        },
        {ifChosen:false,
          id:"c#",
          icon:"el-icon-Cyuyan"
        },
        {ifChosen:false,
          id:"其他",
          icon:"el-icon-more-outline"
        },],
      ifLogin:false,
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
    chooseTag(e,index){
      this.tags[index].ifChosen = !this.tags[index].ifChosen;
      this.tagChoice.push(this.tags[index].id)
      console.log(this.tagChoice)
      let target = e.target;
      if(target.nodeName === "I"||target.nodeName === "svg"){
        target = e.target.parentNode;
      }
      target.blur();
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log(this.ruleForm)
          this.ruleForm.label = this.tagChoice
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
      this.ifLogin = false;
    }else{
      this.ifLogin = true;
      let url = 'http://localhost:8081/blog/'+blogId
      request.get(url).then(res=>{
        const blog = res.data
        this.ruleForm.id = blog.id
        this.ruleForm.title = blog.title
        this.ruleForm.description = blog.description
        this.ruleForm.content = blog.content
        blog.label = JSON.parse(blog.label)
        for(let i = 0;i<this.tags.length;i++){
          for(let j = 0;j<blog.label.length;j++){
            if(blog.label[j] === this.tags[i].id){
              this.tags[i].ifChosen = true;
            }
          }
        }
        this.$forceUpdate()
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
