import {createRouter, createWebHistory} from 'vue-router';
import LoginComponent from "@/LoginManagement/LoginPage.vue";
import HomePage from "@/components/HomePage.vue";
import StuInfoPage from "@/StudentManagement/StudentHome/StuInfoPage.vue";
import TeaInfoPage from "@/TeacherManagement/TeacherHome/TeaInfoPage.vue";
import HomeworkList from "@/StudentManagement/StudentHomework/HomeworkList.vue";
import TeaManagement from "@/AdminManagement/AdminActivity/TeaManagement.vue";
import HomManagement from "@/AdminManagement/AdminActivity/HomManagement.vue";
import CouManagement from "@/AdminManagement/AdminActivity/CouManagement.vue";
import StuManagement from "@/AdminManagement/AdminActivity/StuManagement.vue";
import AdminfoPage from "@/AdminManagement/AdminHome/AdminfoPage.vue";
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
                    component: StuInfoPage
                },

                {
                    path: 'viewHomework',
                    name:'ViewHomework',
                    component: HomeworkList
                }
            ]
        },
        {
            path: '/teacherHome',
            name: 'teacherHome',
            component: HomePage,
            children: [
                {
                    path: '',
                    name:'TeacherInfoPage',
                    component: TeaInfoPage
                },
            ]
        },
        {
            path: '/adminHome',
            name: 'adminHome',
            component: HomePage,
            children: [
                {
                    path: '/AdminInfoPage',
                    name:'AdminInfoPage',
                    component: AdminfoPage
                },
                {
                    path: '/StuManagement',
                    name:'StuManagement',
                    component: StuManagement
                },
                {
                    path: '/TeaManagement',
                    name:'TeaManagement',
                    component: TeaManagement
                },
                {
                    path: '/HomManagement',
                    name:'HomManagement',
                    component: HomManagement
                },
                {
                    path: '/CouManagement',
                    name:'CouManagement',
                    component: CouManagement

                }
            ]
        },

        {
            path: '/hh',
            name: 'hh',
            component: HomeworkList,
        }

        // {
        //     path: '/adminHome',
        //     name: 'adminHome',
        //     component: AdminPage,
        //     // children: [
        //     //     {
        //     //         path: 'info',
        //     //         name:'AdminInfoPage',
        //     //         component: InfoPage
        //     //     },
        //     // ]
        // }

    ],
    // mode: history

})



export default router;