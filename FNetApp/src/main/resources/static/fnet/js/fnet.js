var app = angular.module('fnet', ['ngRoute', 'checklist-model']);

/**
 * Configure routes and register HTTP interceptor
 */
app.config(['$httpProvider', '$routeProvider', function($httpProvider,$routeProvider) {
	// TODO: HTTP interceptor
	// Define routes
	$routeProvider.when('/admin', {
		templateUrl: '/fnet/admin/main'
	});
	$routeProvider.when('/main', {
		templateUrl: '/fnet/view/main'
	});
}
]);

/**
 * FNet Controller
 */
app.controller('fc', ['$scope', '$rootScope', '$http', function($scope, $rootScope, $http) {
}
]);