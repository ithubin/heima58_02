//定义服务
//ajax发送请求内置服务
app.service("loginService", function ($http) {
    //查询运营商系统用户登录名称
    this.loadLoginName = function () {
        //发送请求
        return $http.get("../login/showName");
    };

})
