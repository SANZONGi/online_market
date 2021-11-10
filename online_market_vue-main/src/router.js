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


export default new VueRouter({
    routes: [
        {
            path: '/',
            redirect: {name: "Gooddetail"},
        },
        {
            path: '/home',
            name: 'Home',
            component: Home,
            meta: {
                requireAuth: false
            }
        },
        {
            path: '/login',
            name: 'Login',
            component: Login,
            meta: {
                requireAuth: true
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
            meta: {requireAuth: false}
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
        }

    ]
})
