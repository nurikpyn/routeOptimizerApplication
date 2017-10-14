angular.module('routeOptimizer')
    .controller('resultsController', function ($timeout, $http) {

        var vm = this;
        vm.loading = false;
        vm.selectedIndex = -1;

        vm.selectFile = selectFile;


        init();

        function init() {
            fetchList();
            $timeout(function () {
                var mymap = L.map('mapid').setView([51.505, -0.09], 13);
            });
        }

        function fetchList() {
            /*$http.get('endpoint').success(function (response) {

            }).error(function (err) {

            });*/

            vm.fileList = ['File name 1', 'File name 2'];
        }

        function selectFile(index) {
            vm.selectedIndex = index;
            vm.loading = true;

            $http.post('fetchMapData-endpoint', vm.fileList[index]).success(function (response) {
                vm.loading = false;
                vm.mapData = response;
            }).error(function (err) {
                //alert(err.message);
                vm.loading = false;
                alert('some error occured');
            });
        }

    });
