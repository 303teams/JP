import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index'
import ElementUI from 'element-plus'
import 'element-plus/theme-chalk/index.css'
import axios from 'axios'
import VueAxios from 'vue-axios'

// axios.defaults.baseURL = 'http://localhost:8082'; // 统一配置的服务器地址

const app = createApp(App);
app.use(router);
app.use(ElementUI)
app.use(VueAxios,axios)
app.mount('#app');


