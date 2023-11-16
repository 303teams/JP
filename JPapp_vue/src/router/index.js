import {createRouter, createWebHistory} from 'vue-router';
import LoginComponent from "@/LoginManagement/LoginPage.vue";
import HomePage from "@/components/HomePage.vue";
import StuInfoPage from "@/StudentManagement/StudentHome/StuInfoPage.vue";
import TeaInfoPage from "@/TeacherManagement/TeacherHome/TeaInfoPage.vue";
import HomeworkList from "@/StudentManagement/StudentHomework/HomeworkList.vue";
import CourseList from "@/StudentManagement/StudentHomework/CourseList.vue";
import HomeworkSubmit from "@/StudentManagement/StudentHomework/HomeworkSubmit.vue";
import HomeworkUpload from "@/TeacherManagement/TeacherHomework/HomeworkUpload.vue";
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
                    path: 'viewCourse',
                    name:'ViewCourse',
                    component: CourseList,
                    props: true,
                },

                {
                    path: 'viewHomework/:cno',
                    name:'ViewHomework',
                    component: HomeworkList,
                    props: true,
                },

                {
                    path: 'HomeworkSubmit/:homeworkID',
                    name:'HomeworkSubmit',
                    component: HomeworkSubmit,
                    props: true,
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
                {
                    path: 'HomeworkUpload',
                    name:'HomeworkUpload',
                    component: HomeworkUpload,
                    props: true,
                }
            ]
        },

        {
            path: '/wangEditor',
            name: 'wangEditor',
            component: () => import('@/components/wangEditor.vue')
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