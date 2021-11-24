import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)
// 告诉 vue “使用” vuex

export default new Vuex.Store({
    state: { // 放置初始状态 app启动的时候的全局的初始值
        token : '',
        userInfo: JSON.parse(sessionStorage.getItem("userInfo"))
    },
    mutations: {
        // set
        SET_TOKEN: (state, token) => {
            state.token = token//修改上面state中的token值，用token值(前端页面传回的值)进行代替更新
            localStorage.setItem("token",token)
        },
        SET_USERINF: (state ,userInfo) => {
            state.userInfo = userInfo
            sessionStorage.setItem("userInfo",JSON.stringify(userInfo))
        },
        REMOVE_INFO: (state) => {
            state.token = ''
            state.userInfo = {}
            localStorage.setItem("token" ,'')
            sessionStorage.setItem("userInfo",JSON.stringify(''))
        }
    },
    getters: {
        getUser: state =>{
            return state.userInfo
        },
        getToken: state =>{
            return state.token
        }
    }
})
