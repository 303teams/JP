import {createRouter, createWebHistory} from 'vue-router';
import LoginComponent from "@/LoginManagement/LoginPage.vue";
import HomePage from "@/components/HomePage.vue";
import StuInfoPage from "@/StudentManagement/StudentHome/StuInfoPage.vue";
import TeaInfoPage from "@/TeacherManagement/TeacherHome/TeaInfoPage.vue";
import HomeworkList from "@/StudentManagement/StudentHomework/HomeworkList.vue";
import CourseList from "@/StudentManagement/StudentHomework/CourseList.vue";
import HomeworkSubmit from "@/StudentManagement/StudentHomework/HomeworkSubmit.vue";
import TeaCourseList from "@/TeacherManagement/TeacherHomework/TeacherCourseList.vue";
import TeaHomeworkList from "@/TeacherManagement/TeacherHomework/TeaHomeworkList.vue";
import HomeworkSubmitList from "@/TeacherManagement/TeacherHomework/HomeworkSubmitList.vue";
import MutualEva from "@/StudentManagement/StudentHomework/MutualEva.vue";
import MessagePage from "@/TeacherManagement/TeacherMessage/MessagePage.vue";
import GradeDetail from "@/StudentManagement/StudentHomework/GradeDetail.vue";
import ViewHomework from "@/StudentManagement/StudentHomework/ViewHomework.vue";
import EvaluationList from "@/StudentManagement/StudentHomework/EvaluationList.vue";
import MessageDetail from "@/TeacherManagement/TeacherMessage/MessageDetail.vue";
import ShowGrade from "@/StudentManagement/StudentGrade/ShowGrade.vue";
import AdminInfoPage from "@/AdminManagement/AdminHome/AdminInfoPage.vue";
import StudentManage from "@/AdminManagement/Management/StudentManage.vue";

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
                    path: 'viewHomeworkList/:cno',
                    name:'ViewHomeworkList',
                    component: HomeworkList,
                    props: true,
                },

                {
                    path: 'viewHomework/:cno/:homeworkID',
                    name:'ViewHomework',
                    component: ViewHomework,
                    props: true,
                },

                {
                    path: 'HomeworkSubmit/:cno/:homeworkID',
                    name:'HomeworkSubmit',
                    component: HomeworkSubmit,
                    props: true,
                },

                {
                    path: 'EvaluationList/:contentID',
                    name:'EvaluationList',
                    component: EvaluationList,
                    props: true,
                },

                {
                    path: 'MutualEva/:homeworkID/:contentID',
                    name:'MutualEva',
                    component: MutualEva,
                    props: true,
                },

                {
                    path: 'GradeDetail/:homeworkID',
                    name:'GradeDetail',
                    component: GradeDetail,
                    props: true,
                },

                {
                    path: 'ShowGrade',
                    name:'ShowGrade',
                    component: ShowGrade,
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
                    path: 'ViewMyHomework/:cno/:courseName',
                    name:'ViewMyHomework',
                    component: TeaHomeworkList,
                    props: true,
                },

                {
                    path: 'ViewHomeworkSubmit/:cno/:homeworkID',
                    name:'ViewHomeworkSubmit',
                    component: HomeworkSubmitList,
                    props: true,
                },

                {
                    path: 'MessagePage',
                    name:'MessagePage',
                    component: MessagePage,
                    props: true,
                },

                {
                    path: 'MessageDetail',
                    name:'MessageDetail',
                    component: MessageDetail,
                    props: true,
                }

            ]
        },

        {
            path: '/AdminHome',
            name: 'AdminHome',
            component: HomePage,
            children: [
                {
                    path: '',
                    name:'AdminInfoPage',
                    component: AdminInfoPage
                },
                {
                    path: 'StudentManage',
                    name:'StudentManage',
                    component: StudentManage,
                    props: true,
                },

            ]
        }




    ],
    // mode: history

})



export default router;