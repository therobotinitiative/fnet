const ACTIVE_GROUP = '/fnet/activegroup';

app.controller('groupController', ['$scope', '$rootScope', '$http', function($scope, $rootScope, $http) {
	$scope.group = {
		active: 0,
		change:function() {
			$http.put(ACTIVE_GROUP +'/' + $scope.current.active_group).then(function(response) {
				if (response.status == 200) {
					$scope.current.root_view = response.data.rootView;
					$scope.current.view = response.data.rootView;
					$scope.latest_container.get();
					$scope.item.get_item(response.data.rootView);
					location.replace('/fnet#!main');
				}
			})
		}
	}
}]);
