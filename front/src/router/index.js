import Vue from 'vue'
import VueRouter from 'vue-router'
import { reissueAccessToken } from '../api/auth'
import DefaultLayout from '@/layouts/default/Index'
import BlankLayout from '@/layouts/blank/Index'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        component: DefaultLayout,
        children: [
            {
                path: '/',
                name: 'home',
                component: () => import(/* webpackChunkName: "about" */ '../views/Home.vue')
            },
            {
                path: '/about',
                name: 'about',
                component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
            },
            {
                path: '/message',
                name: 'message',
                component: () => import(/* webpackChunkName: "messages" */ '../views/Message.vue')
            }
        ]
    },
    {
        path: '/',
        component: BlankLayout,
        children: [
            {
                path: '/signIn',
                name: 'signIn',
                component: () => import(/* webpackChunkName: "login" */ '../views/SignIn.vue'),
                meta: { unauthorized: true }
            },
            {
                path: '/signUp',
                name: 'signUp',
                component: () => import(/* webpackChunkName: "login" */ '../views/SignUp.vue'),
                meta: { unauthorized: true }
            }
        ]
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

router.beforeEach(async (to, from, next) => {
    Vue.prototype.$Progress.start()

    const accessToken = localStorage.getItem('accessToken')
    const refreshToken = localStorage.getItem('accessToken')
    if (accessToken && !refreshToken) {
        await reissueAccessToken()
    }

    if (to.matched.some((record) => record.meta.unauthorized) || accessToken) {
        return next()
    }

    return next('/signIn')
})

router.afterEach(async () => {
    //  finish the progress bar
    Vue.prototype.$Progress.finish()
})

export default router
