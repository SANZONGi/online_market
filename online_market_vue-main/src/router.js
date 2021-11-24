import Vue from 'vue'
import VueRouter from 'vue-router'
import Meta from 'vue-meta'

Vue.use(VueRouter)
Vue.use(Meta)
const Home = () => import('./views/Home')
const Login = () => import('./views/Login')
const Register = () => import('./views/Register')
const Userhome = () => import('./views/Userhome')
const Publish = () => import('./views/Publish')
const Gooddetail = () => import('./views/Gooddetail')
const Changepassword = () => import('./views/Changepassword')
const Buy = () => import('./views/Buy')
const CheckBuyer = () => import('./views/CheckBuyer')
const Historyorders = () => import('./views/Historyorders')
const Historygood = () => import('./views/Historygood')
const Frozen = () => import('./views/Frozen')
const Custhistory = () => import('./views/Custhistory')
const Changecust = () => import('./views/Changecust')
const Checkuser = () => import('./views/Checkuser')
const Userrecords = () => import('./views/Userrecords')

export default new VueRouter({
    routes: [
        {
            path: '/',
            redirect: {name: "Home"},
        },
        {
            path: '/home',
            name: 'Home',
            component: Home,
            meta: {
                requireAuth: false//值为true表示进入这个路由是需要登录的
            }
        },
        {
            path: '/login',
            name: 'Login',
            component: Login,
            meta: {
                requireAuth: false
            }
        },
        {
            path: '/register',
            name: 'Register',
            component: Register,
            meta: {
                requireAuth: false
            }
        },
        {
            path: '/userhome',
            name: 'Userhome',
            component: Userhome,
            meta: {requireAuth: true}
        },
        {
            path: '/publish',
            name: 'Publish',
            component: Publish,
            meta: {requireAuth: true}
        }
        ,
        {
            path: '/gooddetail',
            name: 'Gooddetail',
            component: Gooddetail,
            meta: {requireAuth: false}
        },
        {
            path: '/changepassword',
            name: 'Changepassword',
            component: Changepassword,
            meta: {requireAuth: true}
        },
        {
            path: '/buy',
            name: 'Buy',
            component: Buy,
            meta: {requireAuth: true}
        },
        {
            path: '/checkBuyer',
            name: 'CheckBuyer',
            component: CheckBuyer,
            meta: {requireAuth: true}
        },
        {
            path: '/historyorders',
            name: 'Historyorders',
            component: Historyorders,
            meta: {requireAuth: true}
        },
        {
            path: '/historygood',
            name: 'Historygood',
            component: Historygood,
            meta: {requireAuth: true}
        },
        {
            path: '/frozen',
            name: 'Frozen',
            component: Frozen,
            meta: {requireAuth: true}
        },
        {
            path: '/changecust',
            name: 'Changecust',
            component:Changecust,
            meta: {requireAuth: true}
        },
        {
            path: '/custhistory',
            name: 'Custhistory',
            component:Custhistory,
            meta: {requireAuth: true}
        },
        {
            path: '/checkuser',
            name: 'Checkuser',
            component:Checkuser,
            meta: {requireAuth: true}
        },
        {
            path: '/userrecords',
            name: 'Userrecords',
            component:Userrecords,
            meta: {requireAuth: true}
        }

    ]
})
