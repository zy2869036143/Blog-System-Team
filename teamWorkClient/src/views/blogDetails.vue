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
      <div class="markdown-body" v-html="content"></div>
      <el-divider></el-divider>
      <el-button circle v-if="!ifLike" icon="el-icon-icon" @click.native="like($event)" size="mini">{{blog.praise}}</el-button>
      <el-button circle v-if="ifLike" type="primary" icon="el-icon-icon-copy" @click.native="like($event)" size="mini">{{blog.praise}}</el-button>
      <el-button circle v-if="!ifFavourite" icon="el-icon-shoucang" @click.native="favourite($event)" size="mini">{{blog.favorite}}</el-button>
      <el-button circle v-if="ifFavourite" type="warning" icon="el-icon-shoucang" @click.native="favourite($event)" size="mini">{{blog.favorite}}</el-button>
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
      content:'',
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
      if(!this.ifLike){

        let data = {
          pid: this.user.id,
          pblogid: this.blog.id,
        }
        this.ifLike = !this.ifLike
        console.log(data)
        request.post("http://localhost:8081/praise/addpraiseinfo",data).then(res=>{
          console.log(res.code)
          if(res.code === 200){
            let temp = this.blog;
            temp.created = '';
            request.post("http://localhost:8081/blog/addpraisenum",temp).then(res1=>{
              if(res1.code === 200){
                this.blog.praise = this.blog.praise+1
              }
            })

          }
        })
      }
      else{
        let data = {
          pid: this.user.id,
          pblogid: this.blog.id,
        }
        this.ifLike = !this.ifLike
        console.log(data)
        request.post("http://localhost:8081/praise/delpraiseinfo",data).then(res=>{
          console.log(res.code)
          if(res.code === 200){
            let temp = this.blog;
            temp.created = '';
            request.post("http://localhost:8081/blog/delpraisenum",temp).then(res1=>{
              if(res1.code === 200){
                this.blog.praise = this.blog.praise-1
              }
            })

          }
        })
      }
      this.$forceUpdate()
      let target = e.target;
      if(target.nodeName === "I"||target.nodeName === "svg"){
        target = e.target.parentNode;
      }
      target.blur();
    },

    favourite(e) {
      e.stopPropagation();
      if(!this.ifFavourite){

        let data = {
          fid: this.user.id,
          fblogid: this.blog.id,
        }
        this.ifFavourite = !this.ifFavourite
        console.log(data)
        request.post("http://localhost:8081/favorite/addfavoriteinfo",data).then(res=>{
          console.log(res.code)
          if(res.code === 200){
            let temp = this.blog;
            temp.created = '';
            console.log(temp)
            request.post("http://localhost:8081/blog/addfavoritenum",temp).then(res1=>{
              if(res1.code === 200){
                this.blog.favorite = this.blog.favorite+1
              }
            })

          }
        })
      }
      else{
        let data = {
          fid: this.user.id,
          fblogid: this.blog.id,
        }
        this.ifFavourite = !this.ifFavourite
        console.log(data)
        request.post("http://localhost:8081/favorite/delfavoriteinfo",data).then(res=>{
          console.log(res.code)
          if(res.code === 200){
            let temp = this.blog;
            temp.created = '';
            request.post("http://localhost:8081/blog/delfavoritenum",temp).then(res1=>{
              if(res1.code === 200){
                this.blog.favorite = this.blog.favorite-1
              }
            })

          }
        })
      }

      this.$forceUpdate()
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
      this.blog = res.data
      if(this.blog.userId === this.user.id){
        this.ownBlog = true;
      }else{
        this.ownBlog = false;
      }

      var MardownIt = require("markdown-it")
      var md = new MardownIt()

      var result = md.render(this.blog.content)
      this.content = result
      // this.ownBlog = (blog.userId === this.$store.getters.getUser.id)
      request.get("http://localhost:8081/praise/getpraiseinfo?userid="+this.user.id).then(res1=>{
        if(res1.code === 200){
          let praise = res1.data
            for(let j = 0;j<praise.length;j++){
              if(praise[j].pblogid === this.blog.id){
                this.ifLike = true;
                break;
              }else {
                this.ifLike = false;
              }
            }

          this.$forceUpdate()
        }
      })

      request.post("http://localhost:8081/favorite/getfavoriteinfo?userid="+this.user.id).then(res1=>{
        if(res1.code === 200){
          let favorite = res1.data
          console.log(favorite)
            for(let j = 0;j<favorite.length;j++){
              if(favorite[j].fblogid === this.blog.id){
                this.ifFavourite= true;
                break;
              }else {
                this.ifFavourite = false;
              }
            }
          this.$forceUpdate()
        }
      })
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
