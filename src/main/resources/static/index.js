angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/app/api/v1';

    // $scope.fillTable = function () {
    //     $http.get(contextPath + '/products')
    //         .then(function (response){
    //             // console.log(response);
    //             $scope.ProductsList = response.data;
    //         });
    // };

    $scope.fillTable = function (pageIndex = 1){
        $http({
           url: contextPath + '/products',
           method: 'GET',
           params: {
               // если фильтр существует то ложим в параметы запроса значение поля, иначе ложим нулл
               min_price: $scope.filter ? $scope.filter.min_price : null,
               max_price: $scope.filter ? $scope.filter.max_price : null,
               title_part: $scope.filter ? $scope.filter.title_part : null,
               pageIndex: pageIndex
           }
        }).then(function (response){
                $scope.ProductsPage = response.data;
                $scope.PaginationArray = $scope.generatePagesIndexes(1, $scope.ProductsPage.totalPages);
            })
    }

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++){
            arr.push(i);
        }
        return arr;
    }

    $scope.resetFilter = function (){
        $scope.filter = null;
        $scope.fillTable();
    }

    $scope.deleteProductById = function (productId) {
        $http.delete(contextPath + '/products/' + productId)
            .then(function (response){
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