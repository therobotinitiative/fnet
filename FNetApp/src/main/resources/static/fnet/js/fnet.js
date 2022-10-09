var app = angular.module('fnet', ['ngRoute', 'checklist-model']);

const LATEST = '/fnet/latest';
const COMMENT_ADD = '/fnet/comment/add';
const EVENT_POLL = '/fnet/event';

const LONG_POLL_TIMEOUT = 15000;

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
	$routeProvider.when('/admin/:view?', {
		templateUrl: '/fnet/admin/main',
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
	}
});

/**
 * FNet Controller
 */
app.controller('fc', ['$scope', '$rootScope', '$http', '$routeParams', '$timeout', function($scope, $rootScope, $http, $routeParams, $timeout) {
			// Long poll:https://blog.guya.net/2016/08/08/simple-server-polling-in-angularjs-done-right/
		  var loadTime = 10000,
		      errorCount = 0,
		      loadPromise;
		  var cancelNextLoad = function() {
		    $timeout.cancel(loadPromise);
		  };
		  var nextLoad = function(mill) {
		    mill = mill || loadTime;
		    //Always make sure the last timeout is cleared before starting a new one
		    cancelNextLoad();
		    loadPromise = $timeout($scope.current.event_poll, mill);
		  };
		$scope.current = {
			view: session.root_view,
			root_view: session.root_view,
			user: session.user,
			user_data: session.user_data,
			user_groups: session.groups,
			active_group: session.active_group,
			previous: '',
			current_name: 'todo: get name',
			can_upload: session.can_upload,
			can_comment: session.can_comment,
			can_add_folder: session.can_add_folder,
			event_poll:function() {
				$http.get(EVENT_POLL)
					.then(function(response) {
						// todo: do the magic
						console.log(response.data);
						$scope.current.event_poll();
						nextLoad();
					})
					.catch(function(res) {
						nextLoad(++errorCount * 2 * loadTime);
					});
			},
			get_link:function(item) {
				console.log(item);
				return "#";
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
		if($routeParams.view == undefined) {
			$routeParams.view = $scope.current.root_view;
		}
		$scope.current.view = $routeParams.view;
		$scope.latest_container.get();
		$scope.current.event_poll();
	  //Always clear the timeout when the view is destroyed, otherwise it will keep polling and leak memory
	  $scope.$on('$destroy', function() {
		console.log('stop polling');
	    cancelNextLoad();
	  });
	}
]);

app.directive( 'backButton', function() {
    return {
        restrict: 'A',
        link: function( scope, element, attrs ) {
            element.on( 'click', function () {
                history.back();
                scope.$apply();
            } );
        }
    };
});

/**
 * Capitalize first letter of a string.
 * 
 * @returns Capitalized string
 */
app.filter('capitalize', function() {
    return function(input) {
      return (angular.isString(input) && input.length > 0) ? input.charAt(0).toUpperCase() + input.substr(1).toLowerCase() : input;
    }
});
