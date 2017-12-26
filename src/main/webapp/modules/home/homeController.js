angular.module('routeOptimizer')
    .controller('homeController', function ($http, $state) {
        var vm = this;

        //public scope

        vm.manualObj = {
            "customerList": [{
                "locationName": null,
                "latitude": 0,
                "longitude": 0
                }],
            "vehicleCount": 0,
            "vehicleCapacity": 0,
            "depot": {
                "locationName": null,
                "latitude": 0,
                "longitude": 0
            }
        };

        vm.fileObj = {
            "locationFilePath": "",
            "vehicleCount": 0,
            "vehicleCapacity": 0,
            "depotListSize": 1
        };

        vm.selectedTab = 'FILE';
        vm.selectedFile = '';

        vm.switchTab = switchTab;
        vm.addUser = addUser;
        vm.submit = submit;
        vm.deleteUser = deleteUser;
        vm.reset = reset;

        function switchTab(tab) {
            vm.selectedTab = tab;
        }

        function addUser() {
            var obj = {
                "locationName": null,
                "latitude": 0,
                "longitude": 0
            }
            vm.manualObj.customerList.push(obj);
        }

        function deleteUser(index) {
            vm.manualObj.customerList.splice(index, 1);
        }

        function reset() {
            $state.reload();
        }

        function checkDuplicates() {
            var duplicated = false;
            var combinedList = angular.copy(vm.manualObj.customerList);
            combinedList.push(vm.manualObj.depot);

            angular.forEach(combinedList, function (listItem, listKey) {
                var count = 0;
                angular.forEach(combinedList, function (val, key) {
                    if (angular.equals(listItem, val)) {
                        count++;
                    }
                });
                if (count > 1) {
                    duplicated = true;
                }
            });
            return duplicated;
        }

        function submit() {
        	 var url ;
            if (vm.selectedTab == 'MANUAL') {
            	  url = "http://localhost:8080/routeOptimizerApplication/save";
                console.log(vm.manualObj);
                if (checkDuplicates()) {
                    alert('duplicates found');
                } else {
                
                    $http.post(url, vm.manualObj).success(function (response) {
                        $state.go('results');
                    }).error(function (err) {
                        //alert("some error has occured");
                        //alert(err.message);
                        $state.go('results');
                    });
                }

            } else {
            	  var url;
            	  url = "http://localhost:8080/routeOptimizerApplication/fileSave";
            		alert(url);
            		console.log(vm.fileObj);
                $http.post(url, vm.fileObj).success(function (response) {
                	
                    $state.go('results');
                }).error(function (err) {
                    //alert("some error has occured");
                    //alert(err.message);
                    $state.go('results');
                });

                /*var fd = new FormData();
                fd.append("locationFile", vm.selectedFile);
                fd.append("vehicleData", angular.toJson(vm.fileObj));

                $http.post(url, fd, {
                    withCredentials: true,
                    headers: {
                        'Content-Type': undefined
                    },
                    transformRequest: angular.identity
                }).success(function (response) {
                    $state.go('results');
                }).error(function (err) {
                    alert("some error has occured");
                    //alert(err.message);
                });*/


            }

        }
    })
    .directive("fileread", [function () {
        return {
            scope: {
                fileread: "="
            },
            link: function (scope, element, attributes) {
                element.bind("change", function (changeEvent) {
                    scope.$apply(function () {
                        scope.fileread = changeEvent.target.files[0];
                    });
                });
            }
        }
}]);;
