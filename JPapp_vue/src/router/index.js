import {createRouter, createWebHistory} from 'vue-router';
import LoginComponent from "@/components/LoginComponent";
//import HomePage from "@/components/HomePage.vue";
import StudentPage from "@/components/StudentPage.vue";
import TeacherPage from "@/components/TeacherPage.vue";
import ManagerPage from "@/components/ManagerPage.vue";
import InfoPage from "@/components/InfoPage.vue";
const routerHistory = createWebHistory();

const router = createRouter({
    history: routerHistory,
    routes: [
        {
            path: '/',
            redirect: '/login'
        },
        {
          path: "/login",
          name: "Login",
          component: LoginComponent
        },

        {
            path: '/student',
            name: 'StudentPage',
            component:StudentPage,
            children: [
                {
                    path: 'info',
                    name:'InfoPage',
                    component: InfoPage
                },
                {
                    path: 'teacher',
                    name: 'TeacherPage',
                    component: TeacherPage,
                    meta: { requiresAuth: true, role: 'teacher' },
                },
                {
                    path: 'manager',
                    name: 'ManagerPage',
                    component: ManagerPage,
                    meta: { requiresAuth: true, role: 'manager' },
                },
            ]
        }

    ],
    // mode: history


})

// router.beforeEach((to, from, next) => {
//     if (to.path === '/login') return next()
//     // 获取token
//     const tokenStr = window.sessionStorage.getItem('token')
//     if (!tokenStr) return next('/login')
//     next()
// })

export default router;