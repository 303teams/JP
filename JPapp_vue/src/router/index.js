import {createRouter, createWebHistory} from 'vue-router';
import LoginComponent from "@/components/LoginComponent";
import HomePage from "@/components/HomePage.vue";
// import StudentPage from "@/components/StudentPage.vue";
// import TeacherPage from "@/components/TeacherPage.vue";
//import AdminPage from "@/components/AdminPage.vue";
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
            path: '/studentHome',
            name: 'studentHome',
            component:HomePage,
            children: [
                {
                    path: 'info',
                    name:'InfoPage',
                    component: InfoPage
                },
            ]
        },

        {
            path: '/teacherHome',
            name: 'teacherHome',
            component: HomePage,
            children: [
                {
                    path: 'info',
                    name:'InfoPage',
                    component: InfoPage
                },
            ]
        },

        {
            path: '/adminHome',
            name: 'adminHome',
            component: HomePage,
            children: [
                {
                    path: 'info',
                    name:'InfoPage',
                    component: InfoPage
                },
            ]
        }

    ],
    // mode: history


})



export default router;