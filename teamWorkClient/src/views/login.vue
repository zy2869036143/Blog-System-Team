<template>
  <div class="login_container">
    <div class="box">
      <div class="login_box">
        <!-- 登录表单区域 -->
        <el-form size="mini" v-model="form" label='登录你的博客'>
          <p class="title">登录你的博客</p>
          <el-form-item>
            <el-input placeholder="昵称" v-model="form.username" prefix-icon="el-icon-user" size="medium"></el-input>
          </el-form-item>
          <!-- 密码 -->
          <el-form-item >
            <el-input placeholder="密码" show-password v-model="form.password" prefix-icon="el-icon-key
" size="medium"></el-input>
          </el-form-item>
          <!-- 记住密码 -->
          <el-form-item style="margin-top:-5px">
            <!--          <el-checkbox label="记住密码" class="rememberMe" v-model="form.ifRemember"></el-checkbox>-->
            <router-link to="/login/register" class="id" style="margin-left:0px">没账号？快注册！</router-link>
          </el-form-item>
          <el-form-item style="margin-top:-15px">

          </el-form-item>
          <!-- 登录按钮 -->
          <el-form-item style="margin-top:-5px">
            <el-button type="primary" @click="login" size="medium">登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>

    <vue-particles
      color="#fff"
      :particleOpacity="0.7"
      :particlesNumber="60"
      shapeType="circle"
      :particleSize="4"
      linesColor="#000000"
      :linesWidth="1"
      :lineLinked="true"
      :lineOpacity="0.4"
      :linesDistance="150"
      :moveSpeed="2"
      :hoverEffect="true"
      hoverMode="grab"
      :clickEffect="true"
      clickMode="push"
      class="lizi"
      style="height:100%"
    >
    </vue-particles>
  </div>
</template>
<style scoped>
.login_container {
  background-image: linear-gradient(-180deg, #f5e3e3 0%, #74a2d7 100%);
  width: 100%;
  height: 100%;
  position: fixed;
  background-size: 100% 100%;
  height: 100%;
}
.login_box {
  width: 400px;
  height: 320px;
  background-color: rgba(65, 93, 125, 0);
  border-radius: 5px;
  position: fixed;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
.el-form {
  padding: 32px;
  position: absolute;
  bottom: 0;
  width: 100%;
  box-sizing: border-box;
}
.el-button {
  width: 100%;
}
.code {
  width: 45%;
}
.rememberMe {
  color: #fff;
}
.id{
  color: #fff;
}
.right /deep/ .el-form-item__label{
  text-align: end;
}
.title{
  color: #0a2c50;
  font-size: 20px;
  text-align: center;
  padding-bottom: 10px;
}
</style>
<script>
import request from "../util/request";

export default {
  data() {
    return {
      identification: '',
      form: {
        username:'',
        name: '',
        password: '',
        ifRemember: false
      }
    }
  },
  methods:{
    async login(){
      let flag = true;
      if(this.form.username === ''){
        await this.$message({
          type: "error",
          message: "昵称不能为空"
        })
        flag = false;
      }
      if(this.form.password === ''){
        await this.$message({
          type: "error",
          message: "密码不能为空"
        })
        flag = false;
      }
      request.post("http://localhost:8081/login",this.form).then(res=>{
        if(res.code === 200){
          this.$message({
            type:"success",
            message:"登录成功"
          })
          let user = res.data;
          this.$router.push({
            path:`/mainBlogPage/allBlogs/${encodeURIComponent(JSON.stringify(user))}`,
          })
        }else{
          this.$message({
            type:"error",
            message:res.msg,
          })
        }
      })
    }
  }
}
</script>
