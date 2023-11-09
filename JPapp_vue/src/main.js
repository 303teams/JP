import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index'
import ElementUI from 'element-plus'
import 'element-plus/theme-chalk/index.css'
import axios from 'axios'
import VueAxios from 'vue-axios'
import * as ElIconModules from '@element-plus/icons'
import store from '@/store'
import "lib-flexible-computer";

const app = createApp(App)
//  统一注册el-icon图标
for(let iconName in ElIconModules){
    app.component(iconName,ElIconModules[iconName])
}

router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('token')
    if (!token && to.name !== 'Login'){
        next({ name: 'Login'})
    }else if(token && to.name == 'Login'){
        next({ name: 'studentHome'})
    }else{
        next()
    }
})

app.use(router);
app.use(ElementUI)
app.use(VueAxios,axios)
app.use(store)
app.mount('#app');
axios.defaults.withCredentials = true


