//定义服务
//ajax发送请求内置服务
app.service("brandService", function ($http) {
    //定义查询所有
    this.findAll = function () {
        //发送请求
        return $http.get("../brand/findAll");
    };

    //分页查询
    this.findPage = function (page, rows) {
        //使用$http内置服务向后台发送请求，进行品牌列表分页查询
        return $http.get("../brand/findPage/" + page + "/" + rows);
    };

    //添加
    this.add = function (entity) {
        return $http.post("../brand/add", entity)
    };

    //修改
    this.update = function (entity) {
        return $http.post("../brand/update", entity)
    };

    //根据id查询品牌
    this.findOne = function (id) {
        //使用内置服务方式请求，根据id查询品牌数据
        return $http.get("../brand/findOne/" + id);
    };
    //删除
    this.dele = function (ids) {
        //使用内置服务发送请求，实现删除
        return $http.get("../brand/dele/" + ids);
    };
    //查询品牌数据，进行多项选择
    this.findBrandList = function () {
        return $http.get("../brand/findBrandList");
    }

})
