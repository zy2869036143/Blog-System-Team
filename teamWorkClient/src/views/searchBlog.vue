<template>
  <div>
    <div v-for="(blog,index) in blogs">
      <el-card style="margin-bottom: 10px" shadow="hover" @click.native="cardPush(blog)">
        <h1>{{blog.title}}</h1>
        <h6>{{blog.created}}</h6>
        <p>{{blog.description}}</p>
        <el-button circle v-if="!ifLike[index]" icon="el-icon-icon" @click.native="like($event,index)" size="mini">

        </el-button>
        <el-button circle v-if="ifLike[index]" type="primary" icon="el-icon-icon-copy" @click.native="like($event,index)" size="mini">
          <!--          <svg class="svg" aria-hidden="true">-->
          <!--            <use xlink:href="#el-icon-icon-copy"></use>-->
          <!--          </svg>-->
        </el-button>
        <el-button circle v-if="!ifFavourite[index]" icon="el-icon-shoucang" @click.native="favourite($event,index)" size="mini"></el-button>
        <el-button circle v-if="ifFavourite[index]" type="warning" icon="el-icon-shoucang" @click.native="favourite($event,index)" size="mini"></el-button>

      </el-card>
    </div>


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
      this.blogs = JSON.parse(decodeURIComponent(this.$route.params.blogs));
      console.log(this.blogs)
    }
  },
  methods: {
    load(){
      let data = JSON.parse(decodeURIComponent(this.$route.params.blogs));
      this.blogs = data.searchBlog
      this.user = data.user;
      this.$forceUpdate();
      console.log(this.blogs)
    },
    cardPush(blog){
      let pushData = {
        user: this.user,
        blogId: blog.id,
      }
      this.$router.push({path:`/blogDetails/${encodeURIComponent(JSON.stringify(pushData))}`})
    },
    like(e,index) {
      e.stopPropagation();
      this.ifLike[index] = !this.ifLike[index]
      this.$forceUpdate()
      let target = e.target;
      if(target.nodeName === "I"||target.nodeName === "svg"){
        target = e.target.parentNode;
      }
      target.blur();
    },
    favourite(e,index) {
      e.stopPropagation();
      this.ifFavourite[index] = !this.ifFavourite[index]
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
