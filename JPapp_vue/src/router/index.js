import {createRouter, createWebHistory} from 'vue-router';
import LoginComponent from "@/components/LoginComponent";
import HomePage from "@/components/HomePage.vue";

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
        // {
        //     path: '/',
        //     name: 'WelcomePage',
        //     component: WelcomePage,
        //     children: [
        //         // {
        //         //     path: '/',
        //         //     redirect: '/welcome/login'
        //         // },
        //         {
        //             path: '/welcome',
        //             redirect: '/welcome/login'
        //         },
        //         {
        //             path: '/welcome/login',
        //             name: 'Login',
        //             component: LoginComponent
        //         }
        //     ]
        // },
        {
            path: '/home',
            name: 'HomePage',
            component:HomePage
        }

    ],
    // mode: history
})

export default router;