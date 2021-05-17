import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from '@/store/store.js'
import axios from 'axios'
import vuetify from './plugins/vuetify'
import { formatDate } from '@/utils/filters';
import VueAnalytics from 'vue-analytics'
import ScrollAnimation from '@/page/post/main/directive/scrollanimation'

Vue.directive('scrollanimation',ScrollAnimation)
Vue.filter('formatDate', formatDate);
Vue.config.productionTip = false

Vue.prototype.$Axios = axios;
Vue.use(VueAnalytics,{
  id : 'UA-175649303-1',
  router,

}) 
new Vue({
  el: '#app',

  //라우터 등록 .....
  router,

  components: { App },
  template: '<App/>',
  store,
  vuetify,
  render: h => h(App)
})
