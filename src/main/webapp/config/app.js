var routeOptimizer = angular.module('routeOptimizer', ['ui.router']);

routeOptimizer.config(function ($stateProvider, $urlRouterProvider) {
    $stateProvider.state('home', {
            url: '/home',
            templateUrl: 'modules/home/home.html',
            controller: 'homeController',
            controllerUrl: 'modules/home/homeController.js',
            controllerAs: 'homeVm'
        })
        .state('results', {
            url: '/results',
            templateUrl: 'modules/results/results.html',
            controller: 'resultsController',
            controllerUrl: 'modules/results/resultsController.js',
            controllerAs: 'resultsVm'
        });

    $urlRouterProvider.otherwise('/home');

});

//angular.element(function () {
//    angular.bootstrap(document, ['routeOptimizer']);
//});