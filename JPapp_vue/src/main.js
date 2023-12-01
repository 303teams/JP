import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index'
import ElementUI, {ElMessage} from 'element-plus'
import 'element-plus/theme-chalk/index.css'
import axios from 'axios'
import VueAxios from 'vue-axios'
import * as ElIconModules from '@element-plus/icons'
import store from '@/store'
import "lib-flexible-computer";
import '@/utils/validator.js'
import '@/utils/detectZoom.js'

// import {detectZoom} from "@/utils/detectZoom";

// const m = detectZoom();
// document.body.style.zoom = 100 / Number(m);

const app = createApp(App)
//  统一注册el-icon图标
for(let iconName in ElIconModules){
    app.component(iconName,ElIconModules[iconName])
}


// router.beforeEach((to, from, next) => {
//     const role = localStorage.getItem('role')
//     const token = localStorage.getItem('token')
//     if (!token && to.name !== 'Login'){
//         next({ name: 'Login'})
//     }else if(token && to.name === 'Login'){
//         if(role === 'student')
//             next({ name: 'StudentInfoPage'})
//         else if(role === 'teacher')
//             next({ name: 'TeacherInfoPage'})
//         else
//             next({ name: 'StudentInfoPage'})
//     }else{
//         next()
//     }
// })

app.config.globalProperties.$message = ElMessage;
app.use(router);
app.use(ElementUI)
app.use(VueAxios,axios)
app.use(store)
app.mount('#app');
axios.defaults.withCredentials = true


