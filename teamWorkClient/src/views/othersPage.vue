<template>
  <!--  <el-container style="margin-top: 0">-->
  <!--    -->
  <!--    <el-header>-->
  <!--      -->

  <!--    </el-header>-->

  <!--  </el-container>-->
  <el-container>
    <el-header>

      <h1 style="margin-left: 45px;font-size: 45px">
        <i class="el-icon-user-solid"></i>
        {{user.username}}</h1>
    </el-header>
    <el-container>
      <el-aside>
        <el-card class="box-card" style="height: 500px;width: 260px">
          <div slot="header" class="clearfix">
            <span>个人资料</span>
          </div>
          <div class="text item">
            {{'昵称 ：' + user.username }}
            <div>
              {{'被关注数 ：'+this.getsubnum}}
            </div>
          </div>
        </el-card>
      </el-aside>
      <el-container>
        <el-main>
          <el-tabs v-model="activeName" @tab-click="handleClick" style="margin-right: 80px">
            <el-tab-pane label="ta的动态" name="first">
              <el-timeline>
                <el-timeline-item  placement="top" v-for="blog in blogs" :key="blog.id">
                  <el-card @click.native="cardPush(blog)" shadow="hover">
                    <h2>
                      {{blog.title}}
                    </h2>
                    <p>{{blog.description}}</p>
                  </el-card>
                </el-timeline-item>
              </el-timeline>

            </el-tab-pane>

            <el-tab-pane label="ta的关注" name="third">
              <el-timeline v-if="haveSub">
                <el-timeline-item placement="top" v-for="user in subusers" :key="user.id">
                  <el-card @click.native="cardPush1(user)" shadow="hover">
                    <h4>
                      {{user.username}}
                    </h4>
                  </el-card>
                </el-timeline-item>
              </el-timeline>
              <el-empty v-else></el-empty>

            </el-tab-pane>

          </el-tabs>
        </el-main>
      </el-container>
    </el-container>
  </el-container>
</template>

<script>
import request from "../util/request";

export default {
  name: "personPage",
  data() {
    return {
      user: '',
      haveSub:false,
      activeName: 'first',

      blogs:[{
        title:'',
        description:'',
        created:''
      }],
      subnum:'',

      subusers:[
        {
          username:''
        }
      ]
    }
  },
  created() {
    this.user = JSON.parse(decodeURIComponent(this.$route.params.user))
    this.first()
    this.getsubnum()

  },
  watch:{
    $route(to,from){
      this.user = JSON.parse(decodeURIComponent(this.$route.params.user))
      this.subusers = []
      this.haveSub = false
      this.first()
      this.third()
    },
    deep:true,
    immediate:true,
  },
  methods: {
    getsubnum(){
      request.post('http://localhost:8081/subscribe/get1usersubscribe?id='+this.user.id).then(res=>{
        if(res.code===200){
          console.log(res.data);
          let data = res.data
          let i = 0
          for(i = 0;i<data.length;i++){
            if(data[i][0].sid === this.user.id){
              break
            }
          }
          this.subnum = data[i].length
        }
      })
    },
    handleClick(tab, event) {
      console.log(tab, event);
      if (tab.name === 'first') {
        // 触发‘配置管理’事件
        this.first();
      }
      else if(tab.name==='second') {
        // 触发‘用户管理’事件
        this.second();
      }
      else if(tab.name==='third') {
        // 触发‘用户管理’事件
        this.third();
      }
      else if(tab.name==='fourth') {
        // 触发‘用户管理’事件
        this.fourth();
      }
    },
    first(){
      console.log(this.user.username)
      let postData = {
        username: this.user.username,
      }
      request.post("http://localhost:8081/search",postData).then(res1=>{
        if(res1.code === 200){
          this.blogs = res1.data;
        }
      })
    },
    second(){

    },
    third(){
      request.post('http://localhost:8081/subscribe/getsubscribe?id='+this.user.id).then(res3=>{
        if(res3.code===200){
          console.log(res3.data);
          this.subusers = res3.data;
          this.haveSub = true;
        }else {
          this.subusers = false
        }
      })

    },
    fourth(){

    },
    cardPush(blog){
      let pushData = {
        user: this.user,
        blogId: blog.id,
      }
      this.$router.push({path:`/blogDetails/${encodeURIComponent(JSON.stringify(pushData))}`})
    },
    cardPush1(user1){
      this.$router.push({path:`/othersPage/${encodeURIComponent(JSON.stringify(user1))}`})
    },

  }
}
</script>

<style scoped>
.el-header{
  background-image: url("../assets/bg3.jpg");
  background-size:100% 100%;
  text-align: left;
  line-height: 220px;
  height: 210px !important;
  /*width: 1080px !important;*/
  margin-left: 85px;
  margin-right: 85px;
  margin-top: 0;
}
.el-aside{
  margin-top: 60px;
  margin-left: 85px;
}



</style>
