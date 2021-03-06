//控制层
app.controller('itemCatController', function ($scope, $controller, itemCatService) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        itemCatService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }

    //分页
    $scope.findPage = function (page, rows) {
        itemCatService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findOne = function (id) {
        itemCatService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    }

    //保存
    $scope.save = function () {
        var serviceObject;//服务层对象
        if ($scope.entity.id != null) {//如果有ID
            serviceObject = itemCatService.update($scope.entity); //修改
        } else {
            //把父节点参数传递给后台进行保存
            $scope.entity.parentId = $scope.parentId;
            serviceObject = itemCatService.add($scope.entity);//增加
        }
        serviceObject.success(
            function (response) {
                if (response.success) {
                    //重新查询
                    $scope.findCatListByParentId($scope.parentId);//重新加载
                } else {
                    alert(response.message);
                }
            }
        );
    }


    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        itemCatService.dele($scope.selectIds).success(
            function (response) {
                if (response.success) {
                    $scope.findCatListByParentId($scope.parentId);//重新加载
                    $scope.selectIds = [];
                }
            }
        );
    }

    $scope.searchEntity = {};//定义搜索对象

    //搜索
    $scope.search = function (page, rows) {
        itemCatService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    };

    //定义方法，查询树形列表节点
    $scope.findCatListByParentId = function (parentId) {

        //记录父节点，当添加节点时，此id作为新建节点的父id
        $scope.parentId = parentId;

        //调用服务方法，实现树形列表查询
        itemCatService.findCatListByParentId(parentId).success(function (data) {
            $scope.catList = data;
        })
    };


    //面包屑导航实现思路：
    //1,记录当前所在级别 （在树形菜单列表的哪一级）
    //2,记录每一级节点名称

    //定义初始化变量，记录级别
    //默认初始化就是第一级
    $scope.grade = 1;

    //每查询下级节点，级别就增加一级
    $scope.setGrade = function (value) {
        $scope.grade = value;
    }

    //判断级别
    //不同级别显示不同分类名称
    $scope.selectCatList = function (entity) {
        //判断当前所在级别
        if ($scope.grade == 1) {
            //定义2个对象记录当前级别名称
            $scope.entity_1 = null;
            $scope.entity_2 = null;

        } else if ($scope.grade == 2) {
            //记录2级节点对象
            //entity_1:面包屑第二级节点
            $scope.entity_1 = entity;
            $scope.entity_2 = null;

        } else if ($scope.grade == 3) {
            //记录3级节点对象
            //entity_2:面包屑第三级节点对象
            $scope.entity_2 = entity;
        }

        //查询子节点
        $scope.findCatListByParentId(entity.id);

    }


});
