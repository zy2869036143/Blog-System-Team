<template>
  <div>

    <el-timeline v-if="blogs.length>0 && ifLogin">

      <el-timeline-item :timestamp="blog.created" placement="top" v-for="blog in blogs" :key="blog.id">
        <el-card @click.native="cardPush(blog)" shadow="hover">
          <el-row>
            <div style="float: left">
              <h3>
                {{blog.title}}
              </h3>
              <p>{{blog.description}}</p>

            </div>

            <el-popconfirm
              title="真的要删除你的这条博客吗？"
              style="float: right;margin-top: 25px"
              @confirm="deleteBlog(blog)">
              <el-button slot="reference" @click="stopE($event)" type="danger" plain circle icon="el-icon-delete"></el-button>
            </el-popconfirm>
            <el-button  @click="edit(blog,$event)" style="float: right;margin-top: 25px;margin-right: 10px" type="primary" plain circle icon="el-icon-view
"></el-button>
          </el-row>

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
    edit(blog,e){
      e.stopPropagation()
      this.cardPush(blog)
    },
     deleteBlog(blog){
       request.post('http://localhost:8081/blog/delete?blogId='+blog.id).then(res=>{
         let postData = {
           username: this.user.username,
         }
         request.post("http://localhost:8081/search",postData).then(res1=>{
           if(res1.code === 200){
             this.blogs = res1.data;
           }
         })
       })
     },
     cardPush(blog){
       let pushData = {
         user: this.user,
         blogId: blog.id,
       }
       this.$router.push({path:`/blogDetails/${encodeURIComponent(JSON.stringify(pushData))}`})
     },
     stopE(e){
       e.stopPropagation()
       console.log(e)
       let target = e.target;
       if(target.nodeName === "I"||target.nodeName === "svg"){
         target = e.target.parentNode;
       }
       target.blur();
     }
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
