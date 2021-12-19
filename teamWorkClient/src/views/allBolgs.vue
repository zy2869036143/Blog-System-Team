<template>
  <div style="margin-left: 50px" >
    <div v-if="blogs.length>0">
      <el-pagination class="mpage"
                     small
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
                :data-image="imageUrl[parseInt(Math.random()*11)]">
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
    <el-empty v-if="blogs.length<=0" description="暂无相关博客"></el-empty>

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
      user:'',
      ifLogin:false,
      category:'',
      imageUrl:["https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1a.nipic.com%2F2009-01-06%2F20091617441988_2.jpg&refer=http%3A%2F%2Fpic1a.nipic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1642230043&t=416c0d63f7e60f38019897f75b364ddc",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.soumeitu.com%2Fwp-content%2Fuploads%2F2020%2F07%2F5efd4bc77d65c.jpg&refer=http%3A%2F%2Fwww.soumeitu.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1642230043&t=7ce6cf2083b863c7454eb3152fb38270",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F2019-12-02%2F5de4deb4e3afc.jpg&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1642230043&t=b1d061eb888f4670027e09cf4dc86408",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2F1011%2F0G31G05Z0%2F1FG3105Z0-8-1200.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1642230045&t=43b1ea331d14e764b47e97d978969ac8",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2F1114%2F0G320103A8%2F200G3103A8-1-1200.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1642229936&t=009e0c8708aedff14a2f919b9f23bf66",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2F4k%2Fs%2F02%2F210925010003G64-0-lp.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1642230043&t=76f332818566307ce708cf4bd4d17a31",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2Ftp09%2F210Q6133S9A22-0-lp.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1642230043&t=aeacfff51397344335a293648aaaa936",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2F1113%2F052R0140305%2F20052Q40305-6-1200.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1642230043&t=2b7c92916437b1e4beb468b0c5d5c637",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F53a3c482469c6.jpg%3Fdown&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1642230387&t=6bce90fe314c0d238d6763275b7b2926",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.jj20.com%2Fup%2Fallimg%2F1114%2F0G020114924%2F200G0114924-5-1200.jpg&refer=http%3A%2F%2Fpic.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1642230387&t=d05f7935756bfd89baf25f604ae809cd",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.taopic.com%2Fuploads%2Fallimg%2F110111%2F292-11011103413892.jpg&refer=http%3A%2F%2Fimg.taopic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1642230482&t=38b66854b3df5b0326151fad1a65e990"
      ]
    }
  },

  created() {
    // if(this.$route.params.user){
    //   this.user = JSON.parse(decodeURIComponent(this.$route.params.user))
    // }
    this.load();
  },
  watch:{
    $route(to,from){
      if(to.path !== from.path)
      {
        this.blogs = []
        this.total = 0
        this.pageSize= 6
        this.currentPage = 1
        this.yourComment=''
          this.ifFavourite=[]
        this.ifLike=[]
        this.load()
      }

    },
    deep:true,
    immediate:true,
  },
  methods: {
    cardPush(blog){
      let pushData = {
        user: this.user,
        blogId: blog.id,
      }

      this.$router.push({path:`/blogDetails/${encodeURIComponent(JSON.stringify(pushData))}`})
    },
    load(){
      if(this.$route.params.category)
        this.category = this.$route.params.category
      if(this.category === 'cc++'){
        this.category = 'c/c++'
      }
      if(this.category === 'c'){
        this.category = 'c#'
      }
      console.log(this.category)
      console.log(this.$route.params.user)
      if(localStorage.getItem("user")!==""&&localStorage.getItem("user")){
        this.user = JSON.parse(decodeURIComponent(localStorage.getItem("user")))
        this.ifLogin = true;
      }else {
        if(!this.$route.params.user || JSON.parse(decodeURIComponent(this.$route.params.user)) === ""){
          this.ifLogin = false;
        }else{
          this.user = JSON.parse(decodeURIComponent(this.$route.params.user))
          localStorage.setItem('user',this.$route.params.user);
          this.ifLogin = true
        }
      }
      console.log(this.ifLogin)
      this.page(1)
    },

    page(currentPage) {
      console.log(currentPage)
      let getData = {
        currentPage: currentPage,
      }
      console.log(this.category)
      if (this.category === 'all' || this.category === ''){
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
      }else{
        let labelData = {
          label: this.category
        }
        request.post("http://localhost:8081/blogs/getByClass",labelData).then(res=>{
          console.log(res)
          this.blogs = []
          this.pageSize = 6
          this.currentPage = currentPage
          this.total = res.data.length
          console.log(this.total)

          for(let i = (currentPage-1)*this.pageSize;i<currentPage*this.pageSize && i<res.data.length;i++){
            this.blogs.push(res.data[i]);
          }

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
      }

    },
    like(e,index,blog) {
      e.stopPropagation();
      if(!this.ifLogin){
        this.$message({
          type:'warning',
          message:'请先登录'
        })
        let target = e.target;
        if(target.nodeName === "I"||target.nodeName === "svg"){
          target = e.target.parentNode;
        }
        target.blur();
        return
      }
      if(!this.ifLike[index]){

        let data = {
          pid: this.user.id,
          pblogid: blog.id,
        }
        this.ifLike[index] = !this.ifLike[index]
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
      if(!this.ifLogin){
        this.$message({
          type:'warning',
          message:'请先登录'
        })
        let target = e.target;
        if(target.nodeName === "I"||target.nodeName === "svg"){
          target = e.target.parentNode;
        }
        target.blur();
        return
      }
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

/deep/ .el-pagination.is-background .el-pager li:not(.disabled) {
  background-color: rgba(255,255,255,0);
  color: black;
}
/deep/ .el-pagination.is-background .el-pager li:not(.disabled).active {
  background-color: #3a8ee6;
  border-radius: 360px;

  color: white;
}
/deep/ .el-pagination.is-background .btn-prev{
  background-color: rgba(143,203,255,0);
  color: #0a2c50;
}
/deep/ .el-pagination.is-background .btn-next{
  background-color: rgba(143,203,255,0);
  color: #0a2c50;
}
/deep/ .el-pagination.is-background .number{
  color: white;
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
