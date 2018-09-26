//控制层
app.controller('typeTemplateController', function ($scope,
                                                   $controller,
                                                   typeTemplateService,
                                                   brandService,
                                                   specificationService) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        typeTemplateService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }

    //分页
    $scope.findPage = function (page, rows) {
        typeTemplateService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findOne = function (id) {
        typeTemplateService.findOne(id).success(
            function (response) {
                $scope.entity = response;
                //从数据库查询的数据都是自费字符串数据
                //把品牌数据转换成json对象
                $scope.entity.brandIds = JSON.parse($scope.entity.brandIds);
                //把规格数据转换为json对象
                $scope.entity.specIds = JSON.parse($scope.entity.specIds);
                //扩展属性
                $scope.entity.customAttributeItems = JSON.parse($scope.entity.customAttributeItems);

            }
        );
    }

    //保存
    $scope.save = function () {
        var serviceObject;//服务层对象
        if ($scope.entity.id != null) {//如果有ID
            serviceObject = typeTemplateService.update($scope.entity); //修改
        } else {
            serviceObject = typeTemplateService.add($scope.entity);//增加
        }
        serviceObject.success(
            function (response) {
                if (response.success) {
                    //重新查询
                    $scope.reloadList();//重新加载
                } else {
                    alert(response.message);
                }
            }
        );
    }


    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        typeTemplateService.dele($scope.selectIds).success(
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
        typeTemplateService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    };


    //定义方法，转换json数组格式数据，把数据截取成文字模式进行展示
    //格式：[{"id":16,"text":"TCL"},{"id":13,"text":"长虹"},{"id":14,"text":"海尔"},{"id":19,"text":"创维"},{"id":21,"text":"康佳"},{"id":18,"text":"夏普"},{"id":17,"text":"海信"},
    // {"id":20,"text":"东芝"},{"id":15,"text":"飞利浦"},{"id":22,"text":"LG"}]
    //参数1：json数组数据 （品牌，规格，扩展属性）
    //参数2：text
    $scope.jsonToStr = function (str, key) {
        //把str json字符串转换成json数组对象
        var jsonObj = JSON.parse(str);

        //定义字符串存储截取结果
        var value = "";

        //循环遍历json数组
        for (var i = 0; i < jsonObj.length; i++) {

            //判断如果i大于0，中间使用逗号分隔
            if (i > 0) {
                value += ",";
            }
            //组装结果
            //1,value = "TCL"
            //2,value = "TCL,长虹"
            value += jsonObj[i][key];

        }

        return value;
    };

    //添加模板，下拉框数据
    //$scope.brandList ={data:[{id:'1',text:'联想'},{id:'2',text:'华为'}]};

    //查询品牌数据，进行多项选择
    $scope.findBrandList = function () {
        //调用品牌服务
        brandService.findBrandList().success(function (data) {
            $scope.brandList = {data: data};
        })

    };

    //定义方法，加载规格多项选择数据
    $scope.findSpecList = function () {
        //调用规格服务
        specificationService.findSpecList().success(function (data) {
            //组装下拉框多项选择格式
            $scope.specList = {data: data};
        })
    };
    
    //定义方法
    //扩展属性
    $scope.addTableRow = function () {
       //entity = TbTypeTemplate
       //customAttributeItems
       $scope.entity.customAttributeItems.push({});
    }
    //删除扩展属性中行对象
    $scope.deleTableRow = function (index) {
        $scope.entity.customAttributeItems.splice(index,1);
    }

});	
