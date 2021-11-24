import router from './router';


router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requireAuth)) {
        if (localStorage.getItem('token') && sessionStorage.getItem("userInfo")) {
            if (to.name !== 'Login') next()        //
            else next({                     //
                name: "Userhome"
            })
        } else {
            if (to.name === 'Login') {         //防止循环调用。如果接下来要进入的是登录页面则直接进入
                next()
            } else {
                next({
                    path: 'Login'
                })
            }
        }
    }
    next()
})
