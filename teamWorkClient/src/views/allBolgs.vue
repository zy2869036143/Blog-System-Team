<template>
  <div>
    <div v-for="blog in blogs">
      <el-card style="margin-bottom: 10px" shadow="hover" @click.native="$router.push({path:'/blogDetails', params: {blogId: blog.id}})">



          <h1>{{blog.title}}</h1>
          <h6>{{blog.created}}</h6>

        <p>{{blog.description}}</p>
        <el-button circle v-if="!ifLike" icon="el-icon-icon" @click.native="like($event)" size="mini">

        </el-button>
        <el-button circle v-if="ifLike" type="primary" icon="el-icon-icon-copy" @click.native="like($event)" size="mini">
<!--          <svg class="svg" aria-hidden="true">-->
<!--            <use xlink:href="#el-icon-icon-copy"></use>-->
<!--          </svg>-->
        </el-button>
        <el-button circle v-if="!ifFavourite" icon="el-icon-shoucang" @click.native="favourite($event)" size="mini"></el-button>
        <el-button circle v-if="ifFavourite" type="warning" icon="el-icon-shoucang" @click.native="favourite($event)" size="mini"></el-button>

      </el-card>
    </div>


    <el-pagination class="mpage"
                   background
                   layout="prev, pager, next"
                   :current-page="currentPage"
                   :page-size="pageSize"
                   :total="total"
                   @current-change=page>
    </el-pagination>
  </div>

</template>
<script>
import request from "../util/request";

export default {
  data() {
    return {
      yourComment:'',
      ifFavourite:false,
      ifLike:false,
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
      pageSize: 5
    }
  },

  created() {
    this.page(1)
  },
  methods: {
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
