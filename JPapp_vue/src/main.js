import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index'
import ElementUI from 'element-plus'
import 'element-plus/theme-chalk/index.css'
import axios from 'axios'
import VueAxios from 'vue-axios'
import * as ElIconModules from '@element-plus/icons'
import 'lib-flexible/flexible.js'

const app = createApp(App)
//  统一注册el-icon图标
for(let iconName in ElIconModules){
    app.component(iconName,ElIconModules[iconName])
}

app.use(router);
app.use(ElementUI)
app.use(VueAxios,axios)
app.mount('#app');


