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
import personPage from"../views/personPage"
import searchBlogs from "../views/searchBlog"
import searchUsers from "../views/searchUsers";
import othersPage from "../views/othersPage";
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/login',
      component: loginView
    },
    {
      path: '/info',
      component: info
    },
    {
      path: '/:user?',
      redirect: '/mainBlogPage/allBlogs/:user?'
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
          path: '/mainBlogPage/allBlogs/:user?/:category?',
          component: allBlogs,
        },
        {
          path: '/mainBlogPage/ownBlogs/:user?',
          component: ownBlogs,
        },
        {
          path: '/blogDetails/:data?',
          component: blogDetails
        },
        {
          path: '/editBlogs/:data?',
          component: editBlogs
        },

        {
          path: '/searchBlogs/:blogs?',
          component: searchBlogs
        },
        {
          path: '/searchUsers/:data?',
          component: searchUsers
        },
        {
          path: '/personPage/:user?',
          component: personPage
        },
        {
          path: '/othersPage/:user?',
          component: othersPage
        },
      ],

    },


  ]
})
