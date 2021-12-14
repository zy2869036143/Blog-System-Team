<template>
  <div style="overflow-y: scroll">
    <el-row >
    <el-col  span="12" v-for="(searchUser,index) in users" :key="index">

      <el-card style="margin-bottom: 10px;width: 600px;margin-left: 40px" shadow="hover" @click.native="cardPush(searchUser)">
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
        console.log(this.users)

        request.post("http://localhost:8081/subscribe/getsubscribe?id="+this.user.id).then(res=>{
          if(res.code === 200){
            let subs = res.data
            console.log(subs)
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
        // request.get("http://localhost:8081/praise/getpraiseinfo?userid="+this.user.id).then(res1=>{
        //   if(res1.code === 200){
        //     let praise = res1.data
        //     console.log(praise)
        //     for(let i = 0;i<this.blogs.length;i++){
        //       for(let j = 0;j<praise.length;j++){
        //         if(praise[j].pblogid === this.blogs[i].id){
        //           this.ifLike[i] = true;
        //           break;
        //         }else {
        //           this.ifLike[i] = false;
        //         }
        //       }
        //     }
        //     this.$forceUpdate()
        //   }
        // })

      //   request.post("http://localhost:8081/favorite/getfavoriteinfo?userid="+this.user.id).then(res1=>{
      //     if(res1.code === 200){
      //       let favorite = res1.data
      //       console.log(favorite)
      //       for(let i = 0;i<this.blogs.length;i++){
      //         for(let j = 0;j<favorite.length;j++){
      //           if(favorite[j].fblogid === this.blogs[i].id){
      //             this.ifFavourite[i] = true;
      //             break;
      //           }else {
      //             this.ifFavourite[i] = false;
      //           }
      //         }
      //       }
      //       this.$forceUpdate()
      //     }
      //   })
      })



    },
    cardPush(user){
      let pushData = {
        user: this.user,
        userName: user.username,
      }
      this.$router.push({path:`/personPage/${encodeURIComponent(JSON.stringify(pushData))}`})
    },

    // like(e,index,blog) {
    //   e.stopPropagation();
    //   if(!this.ifLike[index]){
    //
    //     let data = {
    //       pid: this.user.id,
    //       pblogid: blog.id,
    //     }
    //     this.ifLike[index] = !this.ifLike[index]
    //     console.log(data)
    //     request.post("http://localhost:8081/praise/addpraiseinfo",data).then(res=>{
    //       console.log(res.code)
    //       if(res.code === 200){
    //         let temp = blog;
    //         temp.created = '';
    //         console.log(temp)
    //         request.post("http://localhost:8081/blog/addpraisenum",temp).then(res1=>{
    //           if(res1.code === 200){
    //             blog.praise = blog.praise+1
    //           }
    //         })
    //
    //       }
    //     })
    //   }
    //   else{
    //     let data = {
    //       pid: this.user.id,
    //       pblogid: blog.id,
    //     }
    //     this.ifLike[index] = !this.ifLike[index]
    //     console.log(data)
    //     request.post("http://localhost:8081/praise/delpraiseinfo",data).then(res=>{
    //       console.log(res.code)
    //       if(res.code === 200){
    //         let temp = blog;
    //         temp.created = '';
    //         request.post("http://localhost:8081/blog/delpraisenum",temp).then(res1=>{
    //           if(res1.code === 200){
    //             blog.praise = blog.praise-1
    //           }
    //         })
    //
    //       }
    //     })
    //   }
    //   this.$forceUpdate()
    //   let target = e.target;
    //   if(target.nodeName === "I"||target.nodeName === "svg"){
    //     target = e.target.parentNode;
    //   }
    //   target.blur();
    // },
    //
    // favourite(e,index,blog) {
    //   e.stopPropagation();
    //   if(!this.ifFavourite[index]){
    //
    //     let data = {
    //       fid: this.user.id,
    //       fblogid: blog.id,
    //     }
    //     this.ifFavourite[index] = !this.ifFavourite[index]
    //     console.log(data)
    //     request.post("http://localhost:8081/favorite/addfavoriteinfo",data).then(res=>{
    //       console.log(res.code)
    //       if(res.code === 200){
    //         let temp = blog;
    //         temp.created = '';
    //         console.log(temp)
    //         request.post("http://localhost:8081/blog/addfavoritenum",temp).then(res1=>{
    //           if(res1.code === 200){
    //             blog.favorite = blog.favorite+1
    //           }
    //         })
    //
    //       }
    //     })
    //   }
    //   else{
    //     let data = {
    //       fid: this.user.id,
    //       fblogid: blog.id,
    //     }
    //     this.ifFavourite[index] = !this.ifFavourite[index]
    //     console.log(data)
    //     request.post("http://localhost:8081/favorite/delfavoriteinfo",data).then(res=>{
    //       console.log(res.code)
    //       if(res.code === 200){
    //         let temp = blog;
    //         temp.created = '';
    //         request.post("http://localhost:8081/blog/delfavoritenum",temp).then(res1=>{
    //           if(res1.code === 200){
    //             blog.favorite = blog.favorite-1
    //           }
    //         })
    //
    //       }
    //     })
    //   }
    //
    //   this.$forceUpdate()
    //   let target = e.target;
    //   if(target.nodeName === "I"||target.nodeName === "svg"){
    //     target = e.target.parentNode;
    //   }
    //   target.blur();
    // },
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
