//import axios from 'axios'
// let protocol = window.location.protocol
// let host = window.location.host
// let reg = /^localhost+/
// if (reg.test(host))
// {
//     axios.defaults.baseURL = 'http://localhost:8081';
// }
// else {
//     axios.default.baseURL = protocol+'//'+host+":8081";
// }
//axios.defaults.baseURL = 'http://121.5.210.93:8081';
// axios.defaults.baseURL = 'http://localhost:8081';
import axios from 'axios'

// axios.defaults.baseURL="http://121.5.210.93:8081"

// if (process.env.NODE_ENV === "development")
//     axios.defaults.baseURL="http://localhost:8081"
// else







axios.defaults.baseURL="http://121.5.210.93:8081"

axios.interceptors.request.use(
    config=>{
        const token = window.localStorage.getItem("token")
        config.headers["token"] = token;
        return config
    },
    err=>{
        return Promise.reject(err);
    }
);
//拦截Axios发起的所有请求.
axios.interceptors.response.use((config)=>{
    return config
});
export default axios
