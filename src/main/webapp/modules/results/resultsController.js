angular.module('routeOptimizer')
    .controller('resultsController', function ($timeout, $http) {

        var vm = this;
        vm.loading = false;
        vm.selectedIndex = -1;

        vm.selectFile = selectFile;

        vm.fileNameObj = {
                
                    "fileName": null
                 };
        init();

        function init() {
            fetchList();
           
            $timeout(function () {
            	 var map;
                 var vehicleRouteLayerGroup;
                 var intervalTimer;
               	  var mymap = L.map('mapid',{
               		    center: [-25.274398, 133.7751],
               		    zoom: 15
               		    });
               	  L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
               		    attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
               		    }).addTo(mymap);
            });
        }

        function fetchList() {
        	var  url = "http://localhost:8080/getSolvedFileList";
            $http.get(url).success(function (response) {
            	vm.fileList=response;
            }).error(function (err) {

            });

            //vm.fileList = ['File name 1', 'File name 2'];
        }

        function selectFile(index) {
            vm.selectedIndex = index;
            vm.loading = true;
            var  url = "http://localhost:8080/getSolved";
            vm.fileNameObj.fileName=vm.fileList[index];
           //alert(obj.fileName);
            $http.post(url, vm.fileNameObj).success(function (response) {
                vm.loading = false;
                vm.mapData = response;
            }).error(function (err) {
              
                vm.loading = false;
                alert('some error occured');
            });
        }

    });
