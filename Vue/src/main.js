import Vue from 'vue';
import App from './App.vue';
import store from "./store";
import router from './router';
import NowUiKit from './plugins/now-ui-kit';
import VueSession from 'vue-session';
import axios from 'axios';
import { BootstrapVue } from 'bootstrap-vue';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';
Vue.use(BootstrapVue);
Vue.config.productionTip = false;
Vue.prototype.$http = axios;
// Vue.use(IconsPlugin);
Vue.use(VueSession, {persist: true});
Vue.use(NowUiKit);

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
