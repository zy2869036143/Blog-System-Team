<template>
  <el-container style="margin-top: 0">
    <el-header>
      <h2 style="margin-left: 70px">{{user.username}}</h2>

    </el-header>

  </el-container>
</template>

<script>
export default {
  name: "personPage",
  data(){
    return{
      user:{},
    }
  },
  created() {
    this.user = JSON.parse(decodeURIComponent(this.$route.params.user))
  },
  methods:{

  }
}
</script>

<style scoped>
.el-header{
 background-image: url("../assets/bg2.jpg");
  background-size:100% 100%;
  text-align: left;
  line-height: 220px;
  height: 210px !important;
  /*width: 1080px !important;*/
  margin-left: 100px;
  margin-right: 100px;
  margin-top: 0;
}
</style>
