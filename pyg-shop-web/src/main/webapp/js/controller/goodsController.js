//控制层
app.controller('goodsController', function ($scope, $controller, goodsService, itemCatService,typeTemplateService) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        goodsService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }

    //分页
    $scope.findPage = function (page, rows) {
        goodsService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findOne = function (id) {
        goodsService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    }

    //保存
    $scope.save = function () {
        var serviceObject;//服务层对象
        if ($scope.entity.goods.sellerId != null) {//如果有ID
            serviceObject = goodsService.update($scope.entity); //修改
        } else {
            //获取富文本编辑器中值,把这个值绑定到需要保存商品描述对象的字段属性中
            $scope.entity.goodsDesc.introduction = editor.html();
            //保存
            serviceObject = goodsService.add($scope.entity);//增加
        }
        serviceObject.success(
            function (response) {
                if (response.success) {
                    //重新查询
                    //清空基本数据
                    $scope.entity = {};
                    //清空富文本编辑器数据
                    editor.html("");
                } else {
                    alert(response.message);
                }
            }
        );
    }


    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        goodsService.dele($scope.selectIds).success(
            function (response) {
                if (response.success) {
                    $scope.reloadList();//刷新列表
                    $scope.selectIds = [];
                }
            }
        );
    }

    $scope.searchEntity = {};//定义搜索对象

    //搜索
    $scope.search = function (page, rows) {
        goodsService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    };


    //实现商品录入：商品分类三级联动效果
    //1,先查询顶级商品分类数据
    //2,根据选中的节点，查询此节点的子节点

    //1,先查询顶级节点
    $scope.findCat1List = function () {
        itemCatService.findCatListByParentId(0).success(function (data) {
            $scope.cat1List = data;
        })
    }

    //使用$watch监控服务，监控节点数据变化，一旦发现有变化，触发事件，做动态查询
    //2,查询第二级节点
    $scope.$watch('entity.goods.category1Id', function (newValue, oldValue) {
        //newValue: 监控的变量变化后值 （新值）
        //oldVaue:  监控的变量变化前的值
        //调用服务查询二级节点
        itemCatService.findCatListByParentId(newValue).success(function (data) {
            $scope.cat2List = data;
        })
    })

    //3,查询第三级节点
    $scope.$watch('entity.goods.category2Id', function (newValue, oldValue) {
        //调用服务查询二级节点
        itemCatService.findCatListByParentId(newValue).success(function (data) {
            $scope.cat3List = data;
        })
    })

    //监控第三级节点，如果发现第三级节点发生变化，获取第三级节点id,根据第三级节点id查询分类对象，获取模板id
    //一个分类对应一个模板
    $scope.$watch('entity.goods.category3Id', function (newValue, oldValue) {
        //查询商品分类
        itemCatService.findOne(newValue).success(function (data) {
            //模板id从分类查询出后，还需要把模板id保存商品spu表中
            $scope.entity.goods.typeTemplateId = data.typeId;
        })
    });
    
    //监控模板id变化，一旦发现模板id发生变化，根据id查询模板数据
    $scope.$watch('entity.goods.typeTemplateId',function (newValue,oldValue) {
        //根据模板id查询模板数据
        typeTemplateService.findOne(newValue).success(function (data) {
            //获取模板对象
            $scope.typeTemplate = data;
            //获取品牌数据
            $scope.typeTemplate.brandIds = JSON.parse($scope.typeTemplate.brandIds);
        })
    })



})
