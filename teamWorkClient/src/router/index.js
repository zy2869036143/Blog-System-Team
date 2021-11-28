import Vue from 'vue'
import Router from 'vue-router'
import loginView from '@/views/login.vue'
import register from "../views/register";
import mainBlogsPage from "../views/mainBlogPage"
import allBlogs from "../views/allBolgs"
import ownBlogs from "../views/ownBlogs"
import blogDetails from "../views/blogDetails"
import editBlogs from "../views/editBlogs"
import info from "../views/info"
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/login',
      component: loginView
    },
    {
      path: '',
      redirect: 'login'
    },
    {
      path: '/login/register',
      component: register
    },
    {
      path: '/mainBlogPage',
      component: mainBlogsPage,
      children: [
        {
          path: '/mainBlogPage/allBlogs',
          component: allBlogs,
        },
        {
          path: '/mainBlogPage/ownBlogs',
          component: ownBlogs,
        },
        {
          path: '/blogDetails',
          component: blogDetails
        },
        {
          path: '/editBlogs',
          component: editBlogs
        },
        {
          path: '/info',
          component: info
        },
      ],

    },


  ]
})
