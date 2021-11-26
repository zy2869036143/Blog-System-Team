import Vue from 'vue'
import Router from 'vue-router'
import loginView from '@/views/login.vue'
import register from "../views/register";
import mainBlogsPage from "../views/mainBlogPage"
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
      component: mainBlogsPage
    }
  ]
})
