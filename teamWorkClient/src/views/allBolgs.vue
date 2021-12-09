<template>
  <div style="margin-left: 50px">
    <el-pagination class="mpage"
                   background
                   layout="prev, pager, next"
                   :current-page="currentPage"
                   :page-size="pageSize"
                   :total="total"
                   @current-change=page>
    </el-pagination>

    <div v-for="(blog,index) in blogs" class="container">
      <div>
        <card @click.native="cardPush(blog)"
              data-image="https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2Fd1fc2cfbd0fad9775e7a394bc97adb0b1d9f6be15807b-j7OsDp_fw658&refer=http%3A%2F%2Fhbimg.b0.upaiyun.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1641138155&t=e23e2c98fdb1def9448caae85ef3a908">
          <h1 slot="header">
            {{blog.title}}
          </h1>

          <p slot="content">
            {{blog.description}}<br><br>
            {{blog.created}}
          </p>

        </card>
        <div class="po">
          <el-button circle v-if="!ifLike[index]" icon="el-icon-icon" @click.native="like($event,index,blog)" size="medium">{{blog.praise}}</el-button>
          <el-button circle v-if="ifLike[index]" type="primary" icon="el-icon-icon-copy" @click.native="like($event,index,blog)" size="medium">{{blog.praise}}</el-button>
          <el-button circle v-if="!ifFavourite[index]" icon="el-icon-shoucang" @click.native="favourite($event,index,blog)" size="medium">{{blog.favorite}}</el-button>
          <el-button circle v-if="ifFavourite[index]" type="warning" icon="el-icon-shoucang" @click.native="favourite($event,index,blog)" size="medium">{{blog.favorite}}</el-button>

        </div>
      </div>



      <!--      <el-card style="margin-bottom: 10px" shadow="hover" @click.native="cardPush(blog)">-->
<!--          <h1>{{blog.title}}</h1>-->
<!--          <h6>{{blog.created}}</h6>-->
<!--        <p>{{blog.description}}</p>-->
<!--        <el-button circle v-if="!ifLike[index]" icon="el-icon-icon" @click.native="like($event,index)" size="mini">-->

<!--        </el-button>-->
<!--        <el-button circle v-if="ifLike[index]" type="primary" icon="el-icon-icon-copy" @click.native="like($event,index)" size="mini">-->
<!--&lt;!&ndash;          <svg class="svg" aria-hidden="true">&ndash;&gt;-->
<!--&lt;!&ndash;            <use xlink:href="#el-icon-icon-copy"></use>&ndash;&gt;-->
<!--&lt;!&ndash;          </svg>&ndash;&gt;-->
<!--        </el-button>-->
<!--        <el-button circle v-if="!ifFavourite[index]" icon="el-icon-shoucang" @click.native="favourite($event,index)" size="mini"></el-button>-->
<!--        <el-button circle v-if="ifFavourite[index]" type="warning" icon="el-icon-shoucang" @click.native="favourite($event,index)" size="mini"></el-button>-->

<!--      </el-card>-->
    </div>

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
      blogs: [],
      currentPage: 1,
      total: 0,
      pageSize: 5,
      user:"",
    }
  },

  created() {
    this.page(1)
    console.log(this.$route.params.user)
    if(this.$route.params.user){

      this.user = JSON.parse(decodeURIComponent(this.$route.params.user))
    }

  },
  methods: {
    cardPush(blog){
      let pushData = {
        user: this.user,
        blogId: blog.id,
      }
      this.$router.push({path:`/blogDetails/${encodeURIComponent(JSON.stringify(pushData))}`})
    },
    page(currentPage) {
      console.log(currentPage)
      let getData = {
        currentPage: currentPage,
      }
      request.post("http://localhost:8081/blogs",getData).then(res => {

        console.log(res)
        this.blogs = res.data.records
        console.log(this.blogs)
        this.currentPage = res.data.current
        this.total = res.data.total
        this.pageSize = res.data.size
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


p {
  line-height: 1.5em;
}

h1 + p, p + p {
  margin-top: 10px;
}

.container {
}

.card-wrap {
  margin: 10px;
  transform: perspective(800px);
  transform-style: preserve-3d;
  cursor: pointer;
}
.card-wrap:hover .card-info {
  transform: translateY(0);
}
.card-wrap:hover .card-info p {
  opacity: 1;
}
.card-wrap:hover .card-info, .card-wrap:hover .card-info p {
  transition: 0.6s cubic-bezier(0.23, 1, 0.32, 1);
}
.card-wrap:hover .card-info:after {
  transition: 5s cubic-bezier(0.23, 1, 0.32, 1);
  opacity: 1;
  transform: translateY(0);
}
.card-wrap:hover .card-bg {
  transition: 0.6s cubic-bezier(0.23, 1, 0.32, 1), opacity 5s cubic-bezier(0.23, 1, 0.32, 1);
  opacity: 0.8;
}
.card-wrap:hover .card {
  transition: 0.6s cubic-bezier(0.23, 1, 0.32, 1), box-shadow 2s cubic-bezier(0.23, 1, 0.32, 1);
  box-shadow: rgba(255, 255, 255, 0.2) 0 0 40px 5px, white 0 0 0 1px, rgba(0, 0, 0, 0.66) 0 30px 60px 0, inset #333 0 0 0 5px, inset white 0 0 0 6px;
}


.card-info p {
  opacity: 0;
  text-shadow: black 0 2px 3px;
  transition: 0.6s 1.6s cubic-bezier(0.215, 0.61, 0.355, 1);
}
.card-info * {
  position: relative;
  z-index: 1;
}
.card-info:after {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  z-index: 0;
  width: 100%;
  height: 100%;
  background-image: linear-gradient(to bottom, transparent 0%, rgba(0, 0, 0, 0.6) 100%);
  background-blend-mode: overlay;
  opacity: 0;
  transform: translateY(100%);
  transition: 5s 1s cubic-bezier(0.445, 0.05, 0.55, 0.95);
}

.card-info h1 {
  font-family: "Playfair Display",serif;
  font-size: 36px;
  font-weight: 700;
  text-shadow: rgba(0, 0, 0, 0.5) 0 10px 10px;
}
.po{
  text-align: center;
  margin-top: 50px;
}
</style>
