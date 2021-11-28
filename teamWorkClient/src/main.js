// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import icon from "./assets/likeIcon/iconfont.css"
import iconjs from "./assets/likeIcon/iconfont.js"
Vue.config.productionTip = false
Vue.use(ElementUI)
import VueParticles from 'vue-particles'
Vue.use(VueParticles)
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
// use
Vue.use(mavonEditor)
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
