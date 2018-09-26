//定义服务
//ajax发送请求内置服务
app.service("specificationService", function ($http) {
    //定义查询所有
    this.findAll = function () {
        //发送请求
        return $http.get("../specification/findAll");
    };

    //分页查询
    this.findPage = function (page, rows) {
        //使用$http内置服务向后台发送请求，进行规格列表分页查询
        return $http.get("../specification/findPage/" + page + "/" + rows);
    };

    //添加
    this.add = function (entity) {
        return $http.post("../specification/add", entity)
    };

    //修改
    this.update = function (entity) {
        return $http.post("../specification/update", entity)
    };

    //根据id查询规格
    this.findOne = function (id) {
        //使用内置服务方式请求，根据id查询规格数据
        return $http.get("../specification/findOne/" + id);
    };
    //删除
    this.dele = function (ids) {
        //使用内置服务发送请求，实现删除
        return $http.get("../specification/dele/" + ids);
    };
    //加载规格多项选择下拉框
    this.findSpecList = function () {
        return $http.get("../specification/findSpecList");
    }

})
