var app = angular.module('fnet', ['ngRoute', 'checklist-model']);

const LATEST = '/fnet/latest';
const COMMENTS_GET = '/fnet/comment';

/**
 * Intercept HTTP request error and use root scope error dialog.
 * @returns Interceptor
 */
function interceptHttpError($q, $rootScope)
{
	return {
		'responseError': function(error)
		{
			switch(error.status)
			{
				case 401:
					window.location.href = '/login';
					return; // No response
				case 408:
					$rootScope.errorDialog.show('Request timedout', 'Server was unable to answer your request');
					break;
				case 413:
					$rootScope.errorDialog.show('Too large file', 'Ask for upload size limit');
					break;
				case 404:
					$rootScope.errorDialog.show('Infamous 404', 'Something was not found');
					break;
				default:
					if (error.data != null)
					{
						$rootScope.errorDialog.show(error.data.message, error.data.description);
					}
					else
					{
						$rootScope.errorDialog.show('Something went wrong', 'Something wrong do not know what: ' + error.status);
					}
			}
			return $q.reject(error);
		}
	}
}

/**
 * Configure routes and register HTTP interceptor
 */
app.config(['$httpProvider', '$routeProvider', function($httpProvider,$routeProvider) {
	$httpProvider.interceptors.push(interceptHttpError);
	
	// Define routes
	$routeProvider.when('/admin', {
		templateUrl: '/fnet/admin/main',
		controller: 'fc'
	});
	$routeProvider.when('/main/:view?', {
		templateUrl: '/fnet/view/main',
		controller: 'fc'
	});
}
]);

app.run(function($rootScope, $timeout)
{
	/**
	 * Error dialog
	 */
	 $rootScope.errorDialog = {
		visible:false,
		message: '',
		description: '',
		show: function(message, description) {
			$rootScope.errorDialog.message = message;
			$rootScope.errorDialog.description = description;
			$rootScope.errorDialog.visible = true;
		},
		hide: function() {
			$rootScope.errorDialog.message = '';
			$rootScope.errorDialog.description = '';
			$rootScope.errorDialog.visible = false;
		}
	}
	
	/**
	 * Information dialog, can be timed. Can be queued.
	 */
	$rootScope.information = {
		visible: false,
		message: '',
		queue: [],
		/**
		 * Show the information dialog.
		 
		 * @param message Message to display, default is empty
		 * @param timout Set the automatic dialog hide time in milliseconds, below zero (usually -1) disables the automatic hiding
		 */
		show: function(message = '', timeout = 4000)
		{
			if ($rootScope.information.visible == true) {
				$rootScope.information.queue.push(message);
			} else {
				$rootScope.information.message = message;
				$rootScope.information.visible = true;
				// Set the dialog timeout
				if (timeout > -1)
				{
					$timeout(function()
					{
						$rootScope.information.hide();
					}, timeout)
				}
			}
		},
		/**
		 * Hide the information dialog.
		 */
		hide: function()
		{
			$rootScope.information.visible = false;
			var message = $rootScope.information.queue.shift();
			if (message != undefined) {
				$rootScope.information.show(message);
			}
		}
	};
});

/**
 * FNet Controller
 */
app.controller('fc', ['$scope', '$rootScope', '$http', '$routeParams', function($scope, $rootScope, $http, $routeParams) {
		$scope.current = {
			view: session.root_view,
			root_view: session.root_view,
			items: null,
			comments: [],
			user: session.user,
			user_data: session.user_data,
			user_groups: session.groups,
			active_group: session.active_group,
			get:function() {
				$scope.current.items = [];
				$scope.current.comments = [];
				$http.get('/fnet/item/' + $scope.current.view).then(function(response) {
					if (response.status == 200) {
						$scope.current.items = response.data;
					}
				});
				if ($scope.current.view != $scope.current.root_view) {
					console.log('getting comments');
					$http.get(COMMENTS_GET + '/' + $scope.current.view).then(function(response) {
						if (response.status == 200) {
							$scope.current.comments = response.data;
						}
					})
				}
			},
			go:function(view_id) {
				console.log('go: ' + view_id);
				$scope.current.view = view_id;
				$scope.current.get();
			}
		};
		$scope.latest_container = {
			data: null,
			get:function() {
				$http.get(LATEST + '/5').then(function(response) {
					if (response.status == 200) {
						$scope.latest_container.data = response.data;
					}
				})
			}
		};
		if ($routeParams.view != null) {
			$scope.current.view = $routeParams.view;
		}
		$scope.latest_container.get();
		$scope.current.get();
	}
]);