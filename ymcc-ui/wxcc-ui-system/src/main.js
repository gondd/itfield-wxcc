import babelpolyfill from 'babel-polyfill'
import Vue from 'vue'
import App from './App'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
//import 'main.css'
// import 'element-ui/lib/theme-default/index.css'
//import './assets/theme/theme-green/index.css'
import VueRouter from 'vue-router'
import store from './vuex/store'
import Vuex from 'vuex'
//import NProgress from 'nprogress'
//import 'nprogress/nprogress.css'
import routes from './routes'
// import Mock from './mock'
// Mock.bootstrap();
import 'font-awesome/css/font-awesome.min.css'
//自己的样式
//import '../src/styles/page.css'
import axios from 'axios'
//配置axios的全局基本路径
axios.defaults.baseURL='http://localhost:10010/wxcc';
//全局属性配置，在任意组件内可以使用this.$http获取axios对象
Vue.prototype.$http = axios

import PartUpload from 'upload.vue'
Vue.component("PartUpload", PartUpload);
//处理Token===================================================================================================
function toLogin(){
    alert("登录失效,请重新登录");
    localStorage.clear();
    router.replace({ path:"/login" });
}
//刷新Token
async function getNewToken() {
    var refreshToken = localStorage.getItem('token');
    let user = JSON.parse(localStorage.getItem("login"));

    if(refreshToken){
        return await axios({
            url: '/uaa/login/refresh',
            method: 'post',
            data:{
                refreshToken:refreshToken,
                username: user.username
            }
        })
    }else{
        toLogin();
    }
}

async function doRequest () {
    try {
        //获取新的Token
        const data = await getNewToken();

        var token = data.data.data.token;
        var refresh_token = data.data.data.token;
        var expiresTime = data.data.data.expiresTime;

        localStorage.setItem("expiresTime",expiresTime);
        localStorage.setItem("U-TOKEN",token);
        localStorage.setItem("R-TOKEN",refresh_token);
        //继续执行上一次失败的请求
    } catch(err) {
        toLogin();
    }
}
axios.interceptors.response.use(result=>{
    console.log(result.data+"jjjjjjj");
    let data = result.data;
    if(!data.success && data.message==="nologin") {
        router.push({path: '/login'});
    }
    if(!data.success && data.message==="nopermission") {
        router.push({path: '/npl'});
    }
    return result;
},error => {
    Promise.reject(error);
})

axios.interceptors.request.use(config => {

   /* //刷新Token请求放行
    if(config.url && config.url.indexOf("refresh") > 0){
        return config;
    }*/

    //如果已经登录了,每次都把token作为一个请求头传递过程
    if (localStorage.getItem('token')) {

        //自动刷新Token

        // 让每个请求携带token--['X-Token']为自定义key 请根据实际情况自行修改
        config.headers['U-TOKEN'] =localStorage.getItem('token')
    }
    return config
}, error => {
    // Do something with request error
    Promise.reject(error)
})

axios.interceptors.response.use(config => {
    return config
},error => {
    if (error && error.response) {
        console.log(error);
        switch (error.response.status) {
            case 401: return toLogin();
        }
    }
    Promise.reject(error)
});


import BaiduMap from 'vue-baidu-map'

Vue.use(BaiduMap, {
    // ak 是在百度地图开发者平台申请的密钥 详见 http://lbsyun.baidu.com/apiconsole/key */
    ak: 'TvDzvZNeapR0NfFRHj7ejjB4odWSH51O'
})
Vue.use(ElementUI)
Vue.use(VueRouter)
Vue.use(Vuex)

//NProgress.configure({ showSpinner: false });

const router = new VueRouter({
    routes
})

//每次路由之前都要执行,每次请求都要经过路由
//每次请求都不拦截到
router.beforeEach((to, from, next) => {

    //NProgress.start();
    if (to.path == '/login') {
        //重新登录,把原来session移除掉
        localStorage.removeItem('token');
        localStorage.removeItem('login');
        localStorage.removeItem('menus');
        localStorage.removeItem('Permission');
    }

    //从session获取用户
    let user = JSON.parse(localStorage.getItem('login'));
    if (!user &&(to.path != '/login' && to.path != '/register') ) {
        //没有获取到,跳转登录路由地址
        next({ path: '/login' })
    } else {
        //已经登录,正常访问
        next()
    }
})


//router.afterEach(transition => {
//NProgress.done();
//});

new Vue({
    //el: '#app',
    //template: '<App/>',
    router,
    store,
    //components: { App }
    render: h => h(App) // index.html id为app的div标签下面使用<App/>和template: '<App/>',一样的效果
}).$mount('#app') // 和el: '#app'效果一样都是挂载在index.html id为app的div标签上面

