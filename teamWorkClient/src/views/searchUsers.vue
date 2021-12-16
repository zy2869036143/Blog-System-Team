<template>
  <div style="overflow: hidden">
    <el-row :gutter="20" v-if="users.length !==0">
    <el-col  :span="12" v-for="(searchUser,index) in users" :key="index">

      <el-card style="margin-bottom: 10px" shadow="hover" @click.native="cardPush(searchUser)">
        <div>
          <el-tag effect="plain" color="#F2F6FC"><i class="el-icon-user-solid"></i>
            {{searchUser.username}}</el-tag>
          <div style="float: right;margin-bottom: 10px">
          <el-button  v-if="ifsubs[index]" type="success" icon="el-icon-check" circle @click.native="subs($event,index,searchUser)"></el-button>
          <el-button  v-if="!ifsubs[index]" type="warning" plain icon="el-icon-star-off" circle @click.native="subs($event,index,searchUser)"></el-button>
          </div>
        </div>

 </el-card>


    </el-col>
  </el-row>
    <el-empty v-else description="暂无相关用户"></el-empty>

  </div>

</template>
<script>
import request from "../util/request";

export default {
  data() {
    return {
      ifsubs:[false],
      currentPage: 1,
      total: 0,
      pageSize: 5,
      user:{},
      search:'',
      users:[],
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
    subs(event,index,searchUser){
      event.stopPropagation();

      if(!this.ifsubs[index]){

        let data = {
          id: this.user.id,
          sid: searchUser.id,
        }
        this.ifsubs[index] = !this.ifsubs[index]
        console.log(data)
        request.post("http://localhost:8081/subscribe/addsubscribe",data).then(res=>{
          console.log(res.code)

        })
      }
      else{
        let data = {
          id: this.user.id,
          sid: searchUser.id,
        }
        this.ifsubs[index] = !this.ifsubs[index]
        console.log(data)
        request.post("http://localhost:8081/subscribe/delsubscribe",data).then(res=>{
          console.log(res.code)

        })
      }
      this.$forceUpdate()
      // let target = e.target;
      // if(target.nodeName === "I"||target.nodeName === "svg"){
      //   target = e.target.parentNode;
      // }
      // target.blur();


    },
    load(){
      let data = JSON.parse(decodeURIComponent(this.$route.params.data));
      this.user = data.user;
      this.search = data.searchKey;
      let postData = {
        username: this.search
      }
      request.post('http://localhost:8081/user/search',postData).then(res=>{
        this.users = res.data;
        console.log(222)
        console.log(this.users)

        request.post("http://localhost:8081/subscribe/getsubscribe?id="+this.user.id).then(res=>{
          if(res.code === 200){
            let subs = res.data
            for(let i = 0;i<this.users.length;i++){
              for(let j = 0;j<subs.length;j++){
                if(subs[j].id === this.users[i].id){
                  this.ifsubs[i] = true;
                  break;
                }else {
                  this.ifsubs[i] = false;
                }
              }
            }
            this.$forceUpdate();
          }
        })

      })



    },
    cardPush(user){
      if(user.id === this.user.id) {
        this.$router.push({path: `/personPage/${encodeURIComponent(JSON.stringify(user))}`})
      }
      else{
        this.$router.push({path:`/othersPage/${encodeURIComponent(JSON.stringify(user))}`})
      }

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
