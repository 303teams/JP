import {createRouter, createWebHistory} from 'vue-router';
import LoginComponent from "@/LoginManagement/LoginPage.vue";
import HomePage from "@/components/HomePage.vue";
import StuInfoPage from "@/StudentManagement/StudentHome/StuInfoPage.vue";
import TeaInfoPage from "@/TeacherManagement/TeacherHome/TeaInfoPage.vue";
import HomeworkList from "@/StudentManagement/StudentHomework/HomeworkList.vue";
import CourseList from "@/StudentManagement/StudentHomework/CourseList.vue";
import CoursePage from "@/TeacherManagement/TeacherHomework/CoursePage.vue";
import HomeworkSubmit from "@/StudentManagement/StudentHomework/HomeworkSubmit.vue";
import TeaCourseList from "@/TeacherManagement/TeacherHomework/TeacherCourseList.vue";
import TeaHomeworkList from "@/TeacherManagement/TeacherHomework/TeaHomeworkList.vue";
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
                    path: 'HomeworkSubmit/:cno/:homeworkID',
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
                    path: 'ViewMyCourse',
                    name:'ViewMyCourse',
                    component: TeaCourseList,
                    props: true,
                },

                {
                    path: 'CoursePage/:cno',
                    name:'CoursePage',
                    component: CoursePage,
                    children:[
                        {
                            path: 'ViewTeaHomework',
                            name:'ViewTeaHomework',
                            component: TeaHomeworkList,
                            props: true,
                        },
                    ],
                    props: true,
                }
            ]
        },


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