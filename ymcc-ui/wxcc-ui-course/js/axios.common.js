axios.defaults.baseURL = "http://127.0.0.1:10010/wxcc"//配置前缀
Vue.prototype.$http = axios //给Vue这个类添加一个原型的属性,这个类的对象都能调用
Vue.config.productionTip = false



var api = {
    getQueryVariable(variable){
        var query = window.location.search.substring(1);
        var vars = query.split("&");
        for (var i=0;i < vars.length;i++) {
            var pair = vars[i].split("=");
            if(pair[0] == variable){return pair[1];}
        }
        return(null);
    },
    getCallUrl(){
        let callUrlName = "callUrl";
        var href = window.location.href;
        if(href.indexOf(callUrlName) > 0){
            return href.substring(href.indexOf(callUrlName)+callUrlName.length+1);
        }
    },
    toLogin(callUrl){
        window.location.href="http://user.ymcc.com:6003/login.html?callUrl="+callUrl;
    },
    noPermission() {
        alert("没访问权限");
    },
    logOut(){
        $.cookie("U-TOKEN",null);
        $.cookie("R-TOKEN",null);
        $.cookie("user",null);
    }
};

let noNeedLoginUrl = ["index.html",""];

//url放行


axios.interceptors.request.use(config => {
    var token = $.cookie('U-TOKEN');
    if (token && token.length >= 0){
        config.headers['U-TOKEN'] = token;
    }
    return config
}, error => {
    Promise.reject(error)
})

axios.interceptors.response.use(res => {
    if(!res.data.success && res.data.message == "nologin") {
        //登录后回跳：检测到你未登录时，将当前页面地址存储到cookie 扩大作用域到二级域名
        $.cookie('jumpUrl',location.href,{
            expires:7,
            path:'/',
            domain:'.wxcc.com',
            secure:false
        });
        //跳到登录页面
        alert("请先登录！");
        location.href = 'http://user.wxcc.com:6003/login.html'
    }
    return res
},error => {
    /*if (error && error.response) {
        if(error.response.status == 401 && error.response.data
            && error.response.data.error && error.response.data.error == "invalid_token"){

            //处理放行
            let currentPage = window.location.href;
            let canPass = false;
            for(let i = 0 ; i < noNeedLoginUrl.length ; i++){
               if(currentPage.indexOf(noNeedLoginUrl[i]) > 0){
                   canPass = true;
                   break;
               }
            }
            if(!canPass){
                alert("登录失效，请先登录");
                let callUrl = window.location.href;
                return api.toLogin(callUrl);
            }

            //token过期情况
            if(error.response.data.error_description && error.response.data.error_description.indexOf("Access token expired")){
                //无感刷新 TODO
                console.log("token过期，刷新Token");
                api.logOut();
            }
        }
        if(error.response.status == 403){
            api.noPermission();
        }
    }*/
    Promise.reject(error)
});



