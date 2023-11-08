import {createRouter, createWebHistory} from 'vue-router';
import LoginComponent from "@/LoginManagement/LoginComponent.vue";
import HomePage from "@/HomeManagement/HomePage.vue";
// import StudentPage from "@/components/StudentPage.vue";
// import TeacherPage from "@/components/TeacherPage.vue";
import AdminPage from "@/components/AdminPage.vue";
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
                    path: '',
                    name:'StudentInfoPage',
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
                    name:'TeacherInfoPage',
                    component: InfoPage
                },
            ]
        },

        {
            path: '/adminHome',
            name: 'adminHome',
            component: AdminPage,
            // children: [
            //     {
            //         path: 'info',
            //         name:'AdminInfoPage',
            //         component: InfoPage
            //     },
            // ]
        }

    ],
    // mode: history

})



export default router;