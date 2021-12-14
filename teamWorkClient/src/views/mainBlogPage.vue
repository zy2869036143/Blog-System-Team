<template>

  <el-container style="height: 100%">
    <el-header >
      <el-menu :default-active="$route.path" style="width: 100%" mode="horizontal"  router>
        <el-submenu v-for="item in subItem"
                      :key="item.id"
                      :index="item.url"
                      :route="{path: item.url, query: {data: item.data}}"
        >
          <template slot="title">
            <i :class="item.icon"></i>
            <span>{{ item.id }}</span>
          </template>
          <el-menu-item v-for="sub in item.subs"
                        :key="sub.id"
                        :index="sub.url"
                        :route="{path: sub.url, query: {data: sub.data}}"
          >
            <template slot="title">
              <i :class="sub.icon"></i>
              <span>{{ sub.id }}</span>
            </template>
          </el-menu-item>
        </el-submenu>

          <el-input placeholder="请输入搜索内容" v-model="search" style="width: 45%;margin-top: 10px;margin-left: 10px">
            <el-select v-model="select" slot="prepend" placeholder="请选择">
              <el-option label="博客" value="1"></el-option>
              <el-option label="用户" value="2"></el-option>
            </el-select>
            <el-button slot="append" icon="el-icon-search" @click="onSearch"></el-button>
          </el-input>

        <el-dropdown v-if="ifLogin">
      <span class="el-dropdown-link">
        <div style="text-align: center">
<!--          <el-avatar :size="25" :src="circleUrl" style="float: left; position: center"></el-avatar>-->
          <div style="float: left; margin-left: 5px;position: center"> {{user.username}}</div>
          <i class="el-icon-arrow-down el-icon--right"></i>
        </div>


      </span>
          <el-dropdown-menu slot="dropdown" :hide-on-click='false'>
            <el-dropdown-item icon="el-icon-24gf-playlistHeart2"  command="a" >
                我的收藏
            </el-dropdown-item>
            <el-dropdown-item icon="el-icon-gerenziliao"  command="b" @click.native="$router.push({
            path:`/personPage/${encodeURIComponent(JSON.stringify(user))}`,
          })">个人主页</el-dropdown-item>
            <el-dropdown-item icon="el-icon-circle-close" command="c" @click.native="logOut()">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>

          <router-link :to="{path:'/login'}" class="router-link" v-if="!ifLogin">登录</router-link>



      </el-menu>

    </el-header>
    <el-main><router-view></router-view></el-main>
  </el-container>
</template>
<style>
.el-header{
  background-color: #545c64;
  padding: 0px;
  margin: 0px;
  border: 0px;
}
.el-dropdown {
  float: right;
  margin-right: 20px;
  margin-top: 18px;
  color: black;
}
.router-link {
  float: right;
  margin-right: 20px;
  margin-top: 18px;
  color: black;
  text-decoration: none;
}
.el-select  {
  width: 80px;
}
.input-with-select .el-input-group__prepend {
  background-color: #fff;
}
</style>

<script>
import request from "../util/request";

export default {
  data(){
    return{
      circleUrl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
      subItem:[],
      select:'1',
      search:'',
      searchBlog:[],
      ifLogin: false,
      user:'',
    }
  },
  created() {
    console.log(localStorage.getItem("user"))
    if(localStorage.getItem("user")!==""&&localStorage.getItem("user")){
      this.user = JSON.parse(decodeURIComponent(localStorage.getItem("user")))
      this.ifLogin = true;
    }else {
      if(!this.$route.params.user){
        this.ifLogin = false;
      }else{
        this.ifLogin = true
        this.user = JSON.parse(decodeURIComponent(this.$route.params.user))
        localStorage.setItem('user',this.$route.params.user);
      }
    }

    let editData = {
      user:this.user,
      blogId:'',
    }
    this.subItem=[
      {
        url:"/mainBlogPage/allBlogs",
        icon:"el-icon-s-grid",
        data:{
        },
        id: "全部博客",
        subs:[
          {url:`/mainBlogPage/allBlogs/${encodeURIComponent(JSON.stringify(this.user))}/${'all'}`,
            data:{
            },
            id:"全部",
            icon:"el-icon-quanbu"
          },
          {url:`/mainBlogPage/allBlogs/${encodeURIComponent(JSON.stringify(this.user))}/${'python'}`,
            data:{
            },
            id:"python",
            icon:"el-icon-python"
          },
          {url:``,
            data:{
            },
            id:"c/c++",
            icon:"el-icon-cyuyan"
          },
          {url:``,
            data:{
            },
            id:"java",
            icon:"el-icon-java"
          },
          {url:``,
            data:{
            },
            id:"dart",
            icon:"el-icon-dart"
          },
          {url:``,
            data:{
            },
            id:"javaScript",
            icon:"el-icon-js"
          },
          {url:``,
            data:{
            },
            id:"swift",
            icon:"el-icon-swift"
          },
          {url:``,
            data:{
            },
            id:"c#",
            icon:"el-icon-Cyuyan"
          },
          {url:``,
            data:{
            },
            id:"其他",
            icon:"el-icon-more-outline"
          },
        ],
      },
      {
        url:`/mainBlogPage/ownBlogs/${encodeURIComponent(JSON.stringify(this.user))}`,
        data:{
        },
        id: "你的博客",
        icon:"el-icon-notebook-1",
        subs:[
          {
            url:`/mainBlogPage/ownBlogs/${encodeURIComponent(JSON.stringify(this.user))}`,
            data:{
            },
            id: "你的博客列表",
            icon:"el-icon-liebiao",
          },
          {
          url:`/editBlogs/${encodeURIComponent(JSON.stringify(editData))}`,
          data:{
          },
          id: "写博客",
            icon:"el-icon-circle-plus-outline"
        }]
      },
    ]
  },
  methods:{
    onSearch(){
      if(this.select === '1'){
        // request.post('http://localhost:8081/blog/selectByKey',this.search).then(res=>{
        //   this.searchBlog = res.data;
        //   console.log(this.searchBlog)
        //   let data = {
        //     user:this.user,
        //     searchBlog:this.searchBlog,
        //   }
        //   this.$router.push({
        //     path:`/searchBlogs/${encodeURIComponent(JSON.stringify(data))}`,
        //   })
        // })
        let data = {
          user:this.user,
          searchKey:this.search,
        }
        this.$router.push({
          path:`/searchBlogs/${encodeURIComponent(JSON.stringify(data))}`,
        })

      }
      else{

        let data = {
          user:this.user,
          searchKey:this.search,
        }
        this.$router.push({
          path:`/searchUsers/${encodeURIComponent(JSON.stringify(data))}`,
        })

      }
    },
    logOut(){
      localStorage.setItem("user","");
      this.$router.replace("/");
      location.reload();
      window.onbeforeunload = function (e) {
        var storage = window.localStorage;
        storage.clear()
      }
    }
  }
}
</script>
