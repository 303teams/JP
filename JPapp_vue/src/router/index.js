import {createRouter, createWebHistory} from 'vue-router';
import WelcomePage from "@/components/WelcomePage";
import LoginComponent from "@/components/LoginComponent";
import RegisterComponent from "@/components/RegisterComponent";
import MarketPage from "@/components/MarketPage";

const routerHistory = createWebHistory();

const router = createRouter({
    history: routerHistory,
    routes: [
        {
            path: '/',
            redirect: '/welcome'
        },
        {
            path: '/welcome',
            name: 'WelcomePage',
            component: WelcomePage,
            children: [
                // {
                //     path: '/',
                //     redirect: '/welcome/login'
                // },
                {
                    path: '/welcome',
                    redirect: '/welcome/login'
                },
                {
                    path: '/welcome/login',
                    name: 'Login',
                    component: LoginComponent
                },
                {
                    path: '/welcome/register',
                    name: 'register',
                    component: RegisterComponent
                }
            ]
        },
        {
            path: '/market',
            name: 'MarketPage',
            component: MarketPage
        }

    ],
    // mode: history
})

export default router;