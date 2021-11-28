<template>
  <div>

    <el-timeline>

      <el-timeline-item :timestamp="blog.created" placement="top" v-for="blog in blogs" :key="blog.id">
        <el-card @click.native="$router.push({path:'/blogDetails', params: {blogId: blog.id}})" shadow="hover">
          <h4>
            <router-link :to="{path:'/blogDetails', params: {blogId: blog.id}}">
              {{blog.title}}
            </router-link>
          </h4>
          <p>{{blog.description}}</p>
        </el-card>
      </el-timeline-item>

    </el-timeline>



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
export default {
  data() {
    return {
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
          title:"22222",
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
}
</script>
<style scoped>

.mpage {
  margin: 0 auto;
  text-align: center;
}

</style>
