import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Element from 'element-ui'
import "element-ui/lib/theme-chalk/index.css"
import "./axios"
import axios from "axios";
import "./guard"

Vue.prototype.$axios = axios
Vue.config.productionTip = false
Vue.use(Element)
new Vue({
  router,
  store,//注册store
  render: h => h(App)
}).$mount('#app')
