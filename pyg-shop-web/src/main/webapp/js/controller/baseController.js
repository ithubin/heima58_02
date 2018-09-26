//定义控制器
app.controller("baseController", function ($scope) {


    //定义方法，此时方法在分页控件中将会被自动执行
    $scope.reloadList = function () {
        //调用分页查询方法
        $scope.findPage($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    };


    //分页控件配置
    $scope.paginationConf = {
        currentPage: 1,
        totalItems: 10,
        itemsPerPage: 10,
        perPageOptions: [10, 20, 30, 40, 50],
        onChange: function () {
            //重新加载,此方法将会被自动加载
            //1,页面刷新
            //2,分页控件中数据发生变化，reloadList也会自动调用
            $scope.reloadList();
        }
    };



    //定义数组变量，封装id
    $scope.selectIds = [];

    //定义方法，此方法将会组装待删除id数据
    $scope.updateSelection = function ($event, id) {
        //判断是否是点击选中事件
        if ($event.target.checked) {
            //选中事件
            $scope.selectIds.push(id);
        } else {
            //取消事件
            $scope.selectIds.splice($scope.selectIds.indexOf(id), 1);
        }
    };



})