<template>
  <div class="register_container">
    <div class="wrapper">
      <div class="container1">
        <p class="title">{{registerText}}</p>
        <form  v-model="form" class="form">

          <input placeholder="昵称" v-model="form.username" prefix-icon="el-icon-s-custom" ></input>

          <input placeholder="邮箱" v-model="form.email" prefix-icon="el-icon-message" ></input>

          <input placeholder="密码" type="password" v-model="form.password" prefix-icon="el-icon-key
" ></input>

          <input placeholder="确认密码" type="password" v-model="checkPwd" prefix-icon="el-icon-key
" ></input>

          <button  @click="register" id="register-button">注册</button>
        </form>
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
@-webkit-keyframes square {
  0% {
    transform: translateY(0);
  }
  100% {
    transform: translateY(-700px) rotate(600deg);
  }
}
@keyframes square {
  0% {
    transform: translateY(0);
  }
  100% {
    transform: translateY(-700px) rotate(600deg);
  }
}
.container1 {
  max-width: 600px;
  margin: 0 auto;
  padding: 80px 0;
  height: 400px;
}
.container1 h1 {
  font-size: 40px;
  transition-duration: 1s;
  transition-timing-function: ease-in-out;
  font-weight: 200;
}
.wrapper {
  width: 400px;
  height: 500px;
  background-color: rgba(65, 93, 125, 0);
  border-radius: 5px;
  position: fixed;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
.form-success .container h1 {
  transform: translateY(85px);
}
form button {
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  outline: 0;
  background-color: white;
  border: 0;
  padding: 10px 15px;
  color: #147897;
  border-radius: 3px;
  width: 400px;
  cursor: pointer;
  font-size: 18px;
  transition-duration: 0.25s;
}
button{
  margin-top: 15px;
}
form button:hover {
  background-color: #f5f7f9;
}
form input {
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  outline: 0;
  border: 1px solid rgba(255, 255, 255, 0.4);
  background-color: rgba(255, 255, 255, 0.2);
  width: 250px;
  border-radius: 3px;
  padding: 10px 15px;
  margin: 0 auto 10px auto;
  display: block;
  text-align: center;
  font-size: 18px;
  color: white;
  transition-duration: 0.25s;
  font-weight: 300;
}
form input:hover {
  background-color: rgba(255, 255, 255, 0.4);
}
form input:focus {
  background-color: white;
  width: 300px;
  color: #25a9ec;
}
.register_container {
  background-image: linear-gradient(to bottom right, #147897 0%, #d0dfe6 100%);
  width: 100%;
  height: 100%;
  position: fixed;
  background-size: 100% 100%;
  height: 100%;
  text-align: center;
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
.addTitle{
  font-size: 50px;
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
      registerText:'注册你的专属blog',
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
      if(this.form.username === ''){
        await this.$message({
          type: "error",
          message: "昵称不能为空"
        })
        flag = false;
      }
      if(!(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(this.form.email)) && this.form.email !== ''){
        await this.$message({
          type: 'error',
          message:'邮箱格式错误'
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
          this.registerText = this.form.username+",恭喜你拥有自己的博客啦"
          $('form').fadeOut(500);
          $('title').addClass('addTitle');
          $('wrapper').addClass('form-success');
          setTimeout(()=>{
            this.$router.push("/login");
          },1800)

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
