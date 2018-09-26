//定义控制器
app.controller("specificationController", function ($scope,$controller,specificationService) {

    //控制器继承
    //参数1：指定控制器继承名称
    //参数2：控制器作用域范围传递 （把父控制器作用域传递给自控制器作用域）
    $controller("baseController",{$scope:$scope});


    //定义函数，查询所有规格数据，展示规格列表
    $scope.findAll = function () {
        specificationService.findAll().success(function (data) {
            $scope.brandList = data;
        })
    };


    //定义分页查询方法
    $scope.findPage = function (page, rows) {
        //调用服务层方法
        specificationService.findPage(page, rows).success(function (data) {
            //把总记录数赋值给分页控件，以便于计算出有多少页
            $scope.paginationConf.totalItems = data.total;
            //记录总记录，页面分页回显
            $scope.specList = data.rows;
        })
    };

    //添加函数
    $scope.add = function () {

        //定义变量，保存保存方法
        var objService = null;

        if ($scope.entity.specification.id != null) {
            //修改
            objService = specificationService.update($scope.entity);
        } else {
            objService = specificationService.add($scope.entity);
        }

        //使用内置服务发送请求，实现规格保存
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

    //根据id查询规格数据
    $scope.findOne = function (id) {
        //调用服务层方法
        specificationService.findOne(id).success(function (data) {
            $scope.entity = data;
        })
    };


    //定义删除方法
    $scope.dele = function () {
        //使用内置服务发送请求，实现删除
        specificationService.dele($scope.selectIds).success(function (data) {
            if (data.success) {
                $scope.reloadList();
            } else {
                alert(data.message);
            }
        })
    };

    //定义集合：初始化
    $scope.entity = {specification:{},specificationOptionList:[]};
    //添加行
    //$scope.entity.specificationOptionList = [{},{},{},....];
    $scope.addTableRow = function () {
        //向数组中添加空对象，表示新增行
        $scope.entity.specificationOptionList.push({});
    };
    
    //删除行
    $scope.deleTableRow = function (index) {
        //删除数组中对象
        $scope.entity.specificationOptionList.splice(index,1);
    }


})