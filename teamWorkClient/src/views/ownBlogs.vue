<template>
  <div>

    <el-timeline v-if="blogs.length>0 && ifLogin">

      <el-timeline-item :timestamp="blog.created" placement="top" v-for="blog in blogs" :key="blog.id">
        <el-card @click.native="cardPush(blog)" shadow="hover">
          <h3>
              {{blog.title}}
          </h3>
          <p>{{blog.description}}</p>
        </el-card>
      </el-timeline-item>

    </el-timeline>

    <el-empty v-if="blogs.length<=0&&ifLogin" description="还没发表博客哦~"></el-empty>

    <el-empty v-if="!ifLogin" description="请先登录哦~"></el-empty>

<!--    <el-pagination class="mpage"-->
<!--                   background-->
<!--                   layout="prev, pager, next"-->
<!--                   :current-page="currentPage"-->
<!--                   :page-size="pageSize"-->
<!--                   :total="total"-->
<!--                   @current-change=page>-->
<!--    </el-pagination>-->
  </div>

</template>
<script>
import request from "../util/request";

export default {
  data() {
    return {
      user:'',
      blogs: [],
      currentPage: 1,
      total: 0,
      pageSize: 5,
      ifLogin:false
    }
  },
   created() {
     console.log(this.$route.params.user)
     if(JSON.parse(decodeURIComponent(this.$route.params.user))===''||!this.$route.params.user){
       this.$message({
         type:"warning",
         message:"请先登录",
       })
       this.ifLogin = false;
     }else{
       this.user = JSON.parse(decodeURIComponent(this.$route.params.user))
       console.log(this.user);
       this.ifLogin = true;
       let postData = {
         username: this.user.username,
       }
       request.post("http://localhost:8081/search",postData).then(res=>{
         if(res.code === 200){
           this.blogs = res.data;
         }
       })
     }

   },
   methods:{
     cardPush(blog){
       let pushData = {
         user: this.user,
         blogId: blog.id,
       }
       this.$router.push({path:`/blogDetails/${encodeURIComponent(JSON.stringify(pushData))}`})
     },
   }
}
</script>
<style scoped>

.mpage {
  margin: 0 auto;
  text-align: center;
}
/deep/ .el-timeline-item__timestamp{
  color: black;
  font-size: 5px;
}
</style>
