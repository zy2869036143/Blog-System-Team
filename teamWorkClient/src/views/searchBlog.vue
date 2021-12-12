<template>
  <div>
    <div v-if="blogs!=null" v-for="(blog,index) in blogs">
      <el-card style="margin-bottom: 10px" shadow="hover" @click.native="cardPush(blog)">
        <h1>{{blog.title}}</h1>
        <h6>{{blog.created}}</h6>
        <p>{{blog.description}}</p>
        <el-button circle v-if="!ifLike[index]" icon="el-icon-icon" @click.native="like($event,index,blog)" size="medium">
          {{blog.praise}}
        </el-button>
        <el-button circle v-if="ifLike[index]" type="primary" icon="el-icon-icon-copy" @click.native="like($event,index,blog)" size="medium">
          <!--          <svg class="svg" aria-hidden="true">-->
          <!--            <use xlink:href="#el-icon-icon-copy"></use>-->
          <!--          </svg>-->
          {{blog.praise}}
        </el-button>
        <el-button circle v-if="!ifFavourite[index]" icon="el-icon-shoucang" @click.native="favourite($event,index,blog)" size="medium">{{blog.favorite}}</el-button>
        <el-button circle v-if="ifFavourite[index]" type="warning" icon="el-icon-shoucang" @click.native="favourite($event,index,blog)" size="medium">{{blog.favorite}}</el-button>

      </el-card>
    </div>
    <el-empty v-if="blogs==null" description="暂无相关博客"></el-empty>

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
      yourComment:'',
      ifFavourite:[],
      ifLike:[],
      blogs: [{
        created:'2021-11-20',
        id:"121",
        title:"22222",
        description:"dsadasdas",
      },
        {
          created:'2021-11-20',
          id:"121",
          title:"22222",
          description:"dsadasdas",
        },
        {
          created:'2021-11-20',
          id:"121",
          title:"22222",
          description:"dsadasdas",
        },
        {
          created:'2021-11-20',
          id:"121",
          title:"33",
          description:"dsadasdas",
        },
        {
          created:'2021-11-20',
          id:"121",
          title:"22222",
          description:"dsadasdas",
        }],
      currentPage: 1,
      total: 0,
      pageSize: 5,
      user:{},
      search:'',
    }
  },

  created() {
    this.load()
  },
  mounted() {
    this.load()
  },
  watch:{
    $route(to,from){
      this.load()
    },
    deep:true,
    immediate:true,
  },
  methods: {
    load(){
      let data = JSON.parse(decodeURIComponent(this.$route.params.blogs));
      // this.blogs = data.searchBlog
      this.user = data.user;
      this.search = data.searchKey;

      request.post('http://localhost:8081/blog/selectByKey',this.search).then(res=>{
        this.blogs = res.data;
        console.log(this.searchBlog)

        request.get("http://localhost:8081/praise/getpraiseinfo?userid="+this.user.id).then(res1=>{
          if(res1.code === 200){
            let praise = res1.data
            console.log(praise)
            for(let i = 0;i<this.blogs.length;i++){
              for(let j = 0;j<praise.length;j++){
                if(praise[j].pblogid === this.blogs[i].id){
                  this.ifLike[i] = true;
                  break;
                }else {
                  this.ifLike[i] = false;
                }
              }
            }
            this.$forceUpdate()
          }
        })

        request.post("http://localhost:8081/favorite/getfavoriteinfo?userid="+this.user.id).then(res1=>{
          if(res1.code === 200){
            let favorite = res1.data
            console.log(favorite)
            for(let i = 0;i<this.blogs.length;i++){
              for(let j = 0;j<favorite.length;j++){
                if(favorite[j].fblogid === this.blogs[i].id){
                  this.ifFavourite[i] = true;
                  break;
                }else {
                  this.ifFavourite[i] = false;
                }
              }
            }
            this.$forceUpdate()
          }
        })
      })

      this.$forceUpdate();

    },
    cardPush(blog){
      let pushData = {
        user: this.user,
        blogId: blog.id,
      }
      this.$router.push({path:`/blogDetails/${encodeURIComponent(JSON.stringify(pushData))}`})
    },

    like(e,index,blog) {
      e.stopPropagation();
      if(!this.ifLike[index]){

        let data = {
          pid: this.user.id,
          pblogid: blog.id,
        }
        this.ifLike[index] = !this.ifLike[index]
        console.log(data)
        request.post("http://localhost:8081/praise/addpraiseinfo",data).then(res=>{
          console.log(res.code)
          if(res.code === 200){
            let temp = blog;
            temp.created = '';
            console.log(temp)
            request.post("http://localhost:8081/blog/addpraisenum",temp).then(res1=>{
              if(res1.code === 200){
                blog.praise = blog.praise+1
              }
            })

          }
        })
      }
      else{
        let data = {
          pid: this.user.id,
          pblogid: blog.id,
        }
        this.ifLike[index] = !this.ifLike[index]
        console.log(data)
        request.post("http://localhost:8081/praise/delpraiseinfo",data).then(res=>{
          console.log(res.code)
          if(res.code === 200){
            let temp = blog;
            temp.created = '';
            request.post("http://localhost:8081/blog/delpraisenum",temp).then(res1=>{
              if(res1.code === 200){
                blog.praise = blog.praise-1
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

    favourite(e,index,blog) {
      e.stopPropagation();
      if(!this.ifFavourite[index]){

        let data = {
          fid: this.user.id,
          fblogid: blog.id,
        }
        this.ifFavourite[index] = !this.ifFavourite[index]
        console.log(data)
        request.post("http://localhost:8081/favorite/addfavoriteinfo",data).then(res=>{
          console.log(res.code)
          if(res.code === 200){
            let temp = blog;
            temp.created = '';
            console.log(temp)
            request.post("http://localhost:8081/blog/addfavoritenum",temp).then(res1=>{
              if(res1.code === 200){
                blog.favorite = blog.favorite+1
              }
            })

          }
        })
      }
      else{
        let data = {
          fid: this.user.id,
          fblogid: blog.id,
        }
        this.ifFavourite[index] = !this.ifFavourite[index]
        console.log(data)
        request.post("http://localhost:8081/favorite/delfavoriteinfo",data).then(res=>{
          console.log(res.code)
          if(res.code === 200){
            let temp = blog;
            temp.created = '';
            request.post("http://localhost:8081/blog/delfavoritenum",temp).then(res1=>{
              if(res1.code === 200){
                blog.favorite = blog.favorite-1
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
  },
}
</script>
<style scoped>

.mpage {
  margin: 0 auto;
  text-align: center;
}
.svg {
  width: 18px; height: 18px;
  vertical-align: -0.2em;
  fill: currentColor;
}

</style>
