<template>

  <el-container>
    <el-header >
      <el-menu :default-active="$route.path" style="width: 100%" mode="horizontal" @select="handleSelect" router>
        <el-menu-item v-for="item in items"
                      :key="item.id"
                      :index="item.url"
                      :route="{path: item.url, query: {data: item.data}}"
        >
          <template slot="title">
            <i class="el-icon-document-copy"></i>
            <span>{{ item.id }}</span>
          </template>
        </el-menu-item>

          <el-input placeholder="请输入搜索内容" v-model="search" style="width: 45%;margin-top: 10px;margin-left: 10px">
            <el-select v-model="select" slot="prepend" placeholder="请选择">
              <el-option label="博客" value="1"></el-option>
              <el-option label="用户" value="2"></el-option>
            </el-select>
            <el-button slot="append" icon="el-icon-search"></el-button>
          </el-input>

        <el-dropdown  @command="handleCommand">
      <span class="el-dropdown-link">
        <div style="text-align: center">
          <el-avatar :size="25" :src="circleUrl" style="float: left; position: center"></el-avatar>
          <div style="float: left; margin-left: 5px;position: center"> {1}</div>
          <i class="el-icon-arrow-down el-icon--right"></i>
        </div>


      </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item icon="el-icon-edit"  command="a" @click.native="dialogVisible=true">个人博客主页</el-dropdown-item>
            <el-dropdown-item icon="el-icon-edit"  command="b" @click.native="dialogVisible=true">个人信息维护</el-dropdown-item>
            <el-dropdown-item icon="el-icon-circle-check" command="c" @click.native="$router.push('/login')">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-menu>

    </el-header>
    <el-main>Main</el-main>
  </el-container>
</template>
<style>
.el-header{
  background-color: #545c64;
  padding: 0px;
  margin: 0px;
  border: 0px;
}
.el-dropdown {
  float: right;
  margin-right: 20px;
  margin-top: 18px;
  color: black;
}
.el-select  {
  width: 80px;
}
.input-with-select .el-input-group__prepend {
  background-color: #fff;
}
</style>

<script>
export default {
  data(){
    return{
      items:[],
      select:'1',
      search:'',
    }
  },
  created() {
    this.items = [
      {
        url:"/blogs",
        data:{
          teacherName:this.name,
          teacherAccount:this.account,
        },
        id: "博客主页"
      },
      {
        url:"/ownBlogs",
        data:{
          teacherName:this.name,
          teacherAccount:this.account,
        },
        id: "个人博客主页"
      },
    ];
  }
}
</script>
