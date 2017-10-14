angular.module('routeOptimizer')
    .controller('resultsController', function ($timeout) {
        $timeout(function () {
            var mymap = L.map('mapid').setView([51.505, -0.09], 13);
        });

    });
