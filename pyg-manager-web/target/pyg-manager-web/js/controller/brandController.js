//定义控制器
app.controller("brandController", function ($scope,$controller,brandService) {

    //控制器继承
    //参数1：指定控制器继承名称
    //参数2：控制器作用域范围传递 （把父控制器作用域传递给自控制器作用域）
    $controller("baseController",{$scope:$scope});


    //定义函数，查询所有品牌数据，展示品牌列表
    $scope.findAll = function () {
        brandService.findAll().success(function (data) {
            $scope.brandList = data;
        })
    };


    //定义分页查询方法
    $scope.findPage = function (page, rows) {
        //调用服务层方法
        brandService.findPage(page, rows).success(function (data) {
            //把总记录数赋值给分页控件，以便于计算出有多少页
            $scope.paginationConf.totalItems = data.total;
            //记录总记录，页面分页回显
            $scope.brandList = data.rows;
        })
    };

    //添加函数
    $scope.add = function () {

        //定义变量，保存保存方法
        var objService = null;

        if ($scope.entity.id != null) {
            //修改
            objService = brandService.update($scope.entity);
        } else {
            objService = brandService.add($scope.entity);
        }

        //使用内置服务发送请求，实现品牌保存
        objService.success(function (data) {
            //判断
            if (data.success) {
                //查询分页列表
                $scope.reloadList();
            } else {
                alert(data.message);
            }
        })
    };

    //根据id查询品牌数据
    $scope.findOne = function (id) {
        //调用服务层方法
        brandService.findOne(id).success(function (data) {
            $scope.entity = data;
        })
    };


    //定义删除方法
    $scope.dele = function () {
        //使用内置服务发送请求，实现删除
        brandService.dele($scope.selectIds).success(function (data) {
            if (data.success) {
                $scope.reloadList();
            } else {
                alert(data.message);
            }
        })
    }


})