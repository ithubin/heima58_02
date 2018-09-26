//定义控制器
app.controller("loginController", function ($scope,loginService) {


    //查询运营商系统用户登录名称
    $scope.findLoginName = function () {
        loginService.loadLoginName().success(function (data) {
            $scope.loginName = data.loginName;
        })
    };

})