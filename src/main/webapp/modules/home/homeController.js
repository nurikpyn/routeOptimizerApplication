angular.module('routeOptimizer')
    .controller('homeController', function ($http, $state) {
        var vm = this;

        //public scope
        vm.manualObj = {
            "customers": [{
                "latitude": "",
                "longitude": ""
                }],
            "noVehicles": 0,
            "vehicleCapacity": 0
        };

        vm.fileObj = {
            "noVehicle": 0,
            "vehicleCapacity": 0
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
                "latitude": "",
                "longitude": ""
            };
            vm.manualObj.customers.push(obj);
        }

        function deleteUser(index) {
            vm.manualObj.customers.splice(index, 1);
        }

        function reset() {
            $state.reload();
        }

        function submit() {
            /*if (vm.selectedTab == 'MANUAL') {
                console.log(vm.manualObj);
            } else {
                var fd = new FormData();
                fd.append("file", vm.selectedFile);
                fd.append("vehicleData", angular.toJson(vm.fileObj));

                $http.post("uploadUrl", fd, {
                    withCredentials: true,
                    headers: {
                        'Content-Type': undefined
                    },
                    transformRequest: angular.identity
                }).success().error();
            }*/
            $state.go('results');
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
