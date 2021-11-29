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
import searchBlogs from "../views/searchBlog"
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/login',
      component: loginView
    },
    {
      path: '',
      redirect: '/mainBlogPage/allBlogs'
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
          path: '/mainBlogPage/allBlogs/:user?',
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
        {
          path: '/searchBlogs/:blogs',
          component: searchBlogs
        },
      ],

    },


  ]
})
