<template>
  <div style="width: 100%">
    <el-card>
      <h2> {{ blog.title }}</h2>
      <el-button type="primary" icon="el-icon-edit" plain v-if="ownBlog"
                 size="mini"
                 @click="handleEdit()">
        编辑
      </el-button>
      <el-divider></el-divider>
      <div class="markdown-body" v-html="blog.content"></div>
      <el-divider></el-divider>
      <el-button circle v-if="!ifLike" icon="el-icon-icon" @click.native="like($event)" size="mini"></el-button>
      <el-button circle v-if="ifLike" type="primary" icon="el-icon-icon-copy" @click.native="like($event)" size="mini"></el-button>
      <el-button circle v-if="!ifFavourite" icon="el-icon-shoucang" @click.native="favourite($event)" size="mini"></el-button>
      <el-button circle v-if="ifFavourite" type="warning" icon="el-icon-shoucang" @click.native="favourite($event)" size="mini"></el-button>
      <el-button circle v-if="!ifComment"  icon="el-icon-pinglun" @click.native="comment($event)" size="mini"></el-button>
      <el-button circle v-if="ifComment" type="info" icon="el-icon-pinglun" @click.native="comment($event)" size="mini"></el-button>
      <div v-if="ifComment">
        <h3>发表你的评论：</h3>
        <el-input type="textarea" v-model="this.yourComment"></el-input>
        <el-button size="mini" style="float: right;margin-top: 10px;margin-bottom: 10px" type="primary">确定</el-button>
      </div>


    </el-card>
    <el-card style="margin-top: 20px">
      <h1>评论：</h1>
      <el-card style="margin-bottom: 10px" shadow="hover" v-for="comment in comments" :key="comment.id">
        <h5>{{comment.who}}</h5>
        <h3>
          {{comment.content}}
        </h3>
      </el-card>
    </el-card>

  </div>
</template>

<script>

import request from "../util/request";

export default {
  data() {
    return {
      ifComment:false,
      ifLike:false,
      ifFavourite:false,
      yourComment:'',
      comments:[{
        content:'23213ee',
        id:'1',
        who:'321313',
      }],
      blog: {
        id: "1",
        title: "111111",
        content: "2222222222"
      },
      user:'',
      ownBlog: true
    }
  },
  methods:{
    handleEdit(){
      let editData = {
        user:this.user,
        blogId:this.blog.id,
      }
      this.$router.push({
        path:`/editBlogs/${encodeURIComponent(JSON.stringify(editData))}`,
      })
    },
    like(e) {
      e.stopPropagation();
      this.ifLike = !this.ifLike
      let target = e.target;
      if(target.nodeName === "I"||target.nodeName === "svg"){
        target = e.target.parentNode;
      }
      target.blur();
    },
    favourite(e) {
      e.stopPropagation();
      this.ifFavourite = !this.ifFavourite
      let target = e.target;
      if(target.nodeName === "I"||target.nodeName === "svg"){
        target = e.target.parentNode;
      }
      target.blur();
    },
    comment(e) {
      e.stopPropagation();
      this.ifComment = !this.ifComment
      let target = e.target;
      if(target.nodeName === "I"||target.nodeName === "svg"){
        target = e.target.parentNode;
      }
      target.blur();
    },
  },
  created() {
    let data =  JSON.parse(decodeURIComponent(this.$route.params.data))
    let blogId = data.blogId
    this.user = data.user
    let url = 'http://localhost:8081/blog/'+blogId
    request.get(url).then(res=>{
      const blog = res.data
      this.blog.id = blog.id
      this.blog.title = blog.title
      this.blog.userId = blog.userId
      console.log(this.user.id)
      console.log(this.blog.userId)
      if(this.blog.userId === this.user.id){
        this.ownBlog = true;
      }else{
        this.ownBlog = false;
      }

      var MardownIt = require("markdown-it")
      var md = new MardownIt()

      var result = md.render(blog.content)
      this.blog.content = result
      // this.ownBlog = (blog.userId === this.$store.getters.getUser.id)
    })
  }
}
</script>

<style scoped>
.mblog {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  width: 100%;
  padding-left: 15px;
  padding-right: 15px;
}

</style>
