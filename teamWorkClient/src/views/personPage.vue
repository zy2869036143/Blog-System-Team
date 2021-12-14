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
              {{'被关注数 ：'+this.subnum}}
            </div>

          </div>
        </el-card>
      </el-aside>
      <el-container>
        <el-main>
          <el-tabs v-model="activeName" @tab-click="handleClick" style="margin-right: 80px">
            <el-tab-pane label="我的动态" name="first">
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
            <el-tab-pane label="我的收藏" name="second">
              <el-timeline>
                <el-timeline-item placement="top" v-for="blog in fblogs" :key="blog.id">
                  <el-card @click.native="cardPush(blog)" shadow="hover">
                    <h3>
                      {{blog.title}}
                    </h3>
                    <p>{{blog.description}}</p>
                  </el-card>
                </el-timeline-item>
              </el-timeline>

            </el-tab-pane>
            <el-tab-pane label="我的关注" name="third">
              <el-timeline>
                <el-timeline-item placement="top" v-for="user in subusers" :key="user.id">
                  <el-card @click.native="cardPush1(user)" shadow="hover">
                    <h4>
                      {{user.username}}
                    </h4>
                  </el-card>
                </el-timeline-item>
              </el-timeline>

            </el-tab-pane>
            <el-tab-pane label="点赞过" name="fourth">
              <el-timeline>
                <el-timeline-item placement="top" v-for="blog in pblogs" :key="blog.id">
                  <el-card @click.native="cardPush(blog)" shadow="hover">
                    <h5>
                      {{blog.title}}
                    </h5>
                    <p>{{blog.description}}</p>
                  </el-card>
                </el-timeline-item>
              </el-timeline>

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
      user: {},
      activeName: 'first',
      pblogs:[
        {
          title:'12121',
          description:'232323',
          created:'2021-03-23'
        }
      ],
      blogs:[{
        title:'',
        description:'',
        created:''
      }],
      fblogs:[
        {
          title:'',
          description:'',
          created:''
        }
      ],

      subusers:[
        {
          username:''
        }
      ],
      subnum:''
    }
  },
  created() {
    this.user = JSON.parse(decodeURIComponent(this.$route.params.user))
    this.first()
    this.getsubnum()
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
      console.log(this.user.id)
      request.post('http://localhost:8081/blog/getuserfavoriteblog?userid='+this.user.id).then(res2=>{
        if(res2.code===200){
          console.log(res2.data)
          this.fblogs=res2.data;
        }
      })
    },
    third(){
      request.post('http://localhost:8081/subscribe/getsubscribe?id='+this.user.id).then(res3=>{
        if(res3.code===200){
          console.log(res3.data);
          this.subusers = res3.data;
        }
      })

    },
    fourth(){
      let form={
        userid: this.user.id,
      }
      console.log(this.user.id)
      request.post('http://localhost:8081/blog/getuserpraiseblog?userid='+this.user.id).then(res4=>{
        if(res4.code===200){
          this.pblogs=res4.data;
        }
      })
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
