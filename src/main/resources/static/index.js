angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/app';

    // $scope.fillTable = function () {
    //     $http.get(contextPath + '/products')
    //         .then(function (response){
    //             // console.log(response);
    //             $scope.ProductsList = response.data;
    //         });
    // };

    $scope.fillTable = function (){
        $http({
           url: contextPath + '/products',
           method: 'GET',
           params: {
               // если фильтр существует то ложим в параметы запроса значение поля, иначе ложим нулл
               min_price: $scope.filter ? $scope.filter.min_price : null,
               max_price: $scope.filter ? $scope.filter.max_price : null
           }
        }).then(function (response){
                $scope.ProductsList = response.data;
            })
    }

    $scope.resetFilter = function (){
        $scope.filter.min_price = null;
        $scope.filter.max_price = null;
        $scope.fillTable();
    }

    $scope.deleteProductById = function (productId) {
        $http({
            url: contextPath + '/products',
            method: 'DELETE',
            params: {
                id: productId
            }
        }).then(function (response){
            $scope.fillTable()
        });
    };

    $scope.submitCreateProduct = function (){
        $http.post(contextPath + '/products', $scope.newProduct)
            .then(function (response){
                $scope.newProduct = null;
                $scope.fillTable();
            });
    };

    $scope.fillTable();
});