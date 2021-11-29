<template>
  <div class="register_container">
    <div class="register_box">
      <el-form size="mini" v-model="form" label='博客'>
        <p class="title">注册你的专属blog</p>

<!--        <el-form-item>-->
<!--          <el-input placeholder="账号" v-model="form.account" prefix-icon="el-icon-user" size="medium"></el-input>-->
<!--        </el-form-item>-->
        <el-form-item>
          <el-input placeholder="昵称" v-model="form.username" prefix-icon="el-icon-s-custom" size="medium"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input placeholder="邮箱" v-model="form.email" prefix-icon="el-icon-message" size="medium"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input placeholder="密码" show-password v-model="form.password" prefix-icon="el-icon-key
" size="medium"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input placeholder="确认密码" show-password v-model="checkPwd" prefix-icon="el-icon-key
" size="medium"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="register" size="medium">注册</el-button>
        </el-form-item>
      </el-form>
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
.register_container {
  background-image: linear-gradient(-180deg, #f5e3e3 0%, #74a2d7 100%);
  width: 100%;
  height: 100%;
  position: fixed;
  background-size: 100% 100%;
  height: 100%;
}
.register_box {
  width: 400px;
  height: 400px;
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
.id{
  color: #fff;
}
.labelColor /deep/ .el-form-item__label{
  color: #fff;
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
      checkPwd: '',
      form: {
        account:'',
        username: '',
        password: '',
        email:'',
        ifRemember: ''
      }
    }
  },
  methods:{
    async register(){
      let flag = true;

      // if(this.form.account === ''){
      //   await this.$message({
      //     type: "error",
      //     message: "账号不能为空"
      //   })
      //   flag = false;
      // }
      if(this.form.name === ''){
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
      if(this.checkPwd === ''){
        await this.$message({
          type: "error",
          message: "确认密码不能为空"
        })
        flag = false;
      }
      if(this.form.password !== this.checkPwd && this.form.password !==''){
        await this.$message({
          type: "error",
          message: "确认密码不相同"
        })
        flag = false;
      }
      request.post("http://localhost:8081/register",this.form).then(res=>{
        if(res.code === 200){
          this.$message({
            type:"success",
            message:"注册成功"
          })
          this.$router.push("/login");
        }else{
          this.$message({
            type:"error",
            message:"用户已存在"
          })
        }
      })
    }
  }
}
</script>
