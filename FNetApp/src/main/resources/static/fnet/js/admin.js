app.controller('adminController', ['$scope', '$http', '$routeParams', '$rootScope', function($scope, $http, $routeParams, $rootScope) {
	/**
	  * Fnet administrator end point constants.
	 */
	const GROUP_GET_ALL = '/fnet/admin/group/all';
	const GROUP_CREATE = '/fnet/admin/group/create';
	const USER_GET_ALL = '/fnet/admin/user/all';
	const USER_ADD = '/fnet/admin/user/';
	const USER_PASSWORD = '/fnet/admin/user/password';
	const USER_DELETE = '/fnet/admin/user';
	const USER_PERMISSIONS = '/fnet/admin/permission';
	const USER_GROUPS = '/fnet/admin/user/groups';
	const USERDATA_GET ='/fnet/admin/userdata';
	const USERDATA_UPDATE = '/fnet/admin/userdata/';
	const PERMISSIONS_ALL = '/fnet/admin/permission/all';
	const UPDATE_PERMISSIONS = '/fnet/admin/permission';
	const USER_GROUPS_UPDATE = '/fnet/admin/user/groups';
	const GROUP_ADD = '/fnet/admin/group/create/';
	const GROUP_DELETE = '/fnet/admin/group';
	
	/**
	 * Container for administration operations.
	 */
	$scope.AdminContainer = {
		/**
		 * Container for currently selected users data. Functions for user related operations.
		 */
		user: {
			current: null,
			data: null,
			groups: null,
			permissions: null,
			custom_permissions: null,
			get_groups:function() {
				$http.get(USER_GROUPS + '/'+ $scope.AdminContainer.user.current.userId).then(function(response) {
					if (response.status == 200) {
						$scope.AdminContainer.user.groups = response.data;
					}
				});
			},
			get_permissions:function() {
				$http.get(USER_PERMISSIONS + '/' + $scope.AdminContainer.user.current.userId).then(function(response) {
					if (response.status == 200) {
						$scope.AdminContainer.user.permissions = response.data.filter(permission => $scope.AdminContainer.all_data.permissions.includes(permission));
						$scope.AdminContainer.user.custom_permissions = response.data.filter(permission => !$scope.AdminContainer.all_data.permissions.includes(permission));
					}
				});
			},
			get_data:function() {
				console.log('getting use data from admin');
				$http.get(USERDATA_GET + '/' + $scope.AdminContainer.user.current.userId).then(function(response) {
					if (response.status == 200) {
						$scope.AdminContainer.user.data = response.data;
					}
				});
			},
			select: function(user) {
				console.log('select user');
				$scope.AdminContainer.user.current = user;
				$scope.AdminContainer.user.get_groups();
				$scope.AdminContainer.user.get_permissions();
				$scope.AdminContainer.user.get_data();
			},
			add:function() {
				var username = document.getElementById('username').value;
				var group_id = document.getElementById('group').value;
				var createUserDTO = {
					'username' : username,
					'groupId' : group_id,
				};
				if (username !== '') {
					$http.post(USER_ADD, createUserDTO).then(function(response) {
						if (response.status == 200) {
							$scope.AdminContainer.all_data.users.push(response.data);
							document.getElementById('username').value = '';
							$rootScope.information.show(username + ' added');
						}
					});
				}
			},
			update:function() {
				var user_data = {
					'userId' : $scope.AdminContainer.user.data.userId,
					'firstName' : $scope.AdminContainer.user.data.firstName,
					'lastName' : $scope.AdminContainer.user.data.lastName,
					'email' : $scope.AdminContainer.user.data.email,
				};
				$http.put(USERDATA_UPDATE, user_data).then(function(response) {
					if (response.status == 200) {
						$rootScope.information.show('User data updated');
					}
				});
			},
			delete:function(user) {
				$http.delete(USER_DELETE + '/' + user.userId).then(function(response) {
					if (response.status == 200) {
						$scope.AdminContainer.all_data.get_users();
						$rootScope.information.show(user.userName + ' deleted');
					}
				});
			},
			update_permissions:function() {
				var permissions = $scope.AdminContainer.user.permissions.concat($scope.AdminContainer.user.custom_permissions);
				$http.put(UPDATE_PERMISSIONS + '/' + $scope.AdminContainer.user.current.userId, permissions).then(function(response) {
					if (response.status == 200) {
						$rootScope.information.show('Permissions updated');
					}
				});

			},
			update_groups:function() {
				var data = {
					'groups' : $scope.AdminContainer.user.groups,
				};
				$http.put(USER_GROUPS_UPDATE + '/' + $scope.AdminContainer.user.current.userId, $scope.AdminContainer.user.groups).then(function(response) {
					if (response.status == 200) {
						$rootScope.information.show('User groups updated');
					}
				});
			},
			password:function() {
				var password = document.getElementById('password').value;
				$http.put(USER_PASSWORD + '/' + $scope.AdminContainer.user.current.userId + '/' + (password != '' ? password :'generate')).then(function(response) {
					if(response.status == 200) {
						$rootScope.information.show('User password changed: ' + response.data.password, -1);
						document.getElementById('password').value = '';
					} else {
						$rootScope.information.show('Password setting failed');
					}
				});
			},
			add_permission:function() {
				var permission = document.getElementById('custom_permission').value;
				if (permission !== '') {
					$scope.AdminContainer.user.custom_permissions.push(permission);
				}
				document.getElementById('custom_permission').value = '';
			},
			delete_permission:function(permission) {
				var temp_array = $scope.AdminContainer.user.custom_permissions.filter(perm => perm != permission);
				$scope.AdminContainer.user.custom_permissions = temp_array;
			}
		},
		group: {
			add:function() {
				var group_name = document.getElementById('groupname').value;
				var data ={'groupName' : group_name}
				if (group_name != '') {
					$http.post(GROUP_ADD, data).then(function(response) {
						if (response.status == 200) {
							$scope.AdminContainer.all_data.groups.push(response.data);
							document.getElementById('groupname').value = '';
							$rootScope.information.show('Group added');
						}
					});
				}
			},
			delete:function(group) {
				$http.delete(GROUP_DELETE + '/' + group.groupId).then(function(response) {
					if (response.status == 200) {
						$rootScope.information.show('Group deleted');
						$scope.AdminContainer.all_data.groups = $scope.AdminContainer.all_data.groups.filter(groupl => groupl != group);
					}
				})
			}
		},
		/**
		 * Container for all available data sets (users, groups, permissions).
		 */
		all_data: {
			users: null,
			groups: null,
			permissions: null,
			get_users: function() {
				$http.get(USER_GET_ALL).then(function(response) {
					if (response.status == 200) {
						$scope.AdminContainer.all_data.users = response.data;
					}
				});
			},
			get_groups: function() {
				$http.get(GROUP_GET_ALL).then(function(response) {
					if(response.status == 200) {
						$scope.AdminContainer.all_data.groups = response.data;
					}
				});
			},
			get_permissions:function() {
				$http.get(PERMISSIONS_ALL).then(function(response) {
					if (response.status == 200) {
						$scope.AdminContainer.all_data.permissions = response.data;
					}
				})
			},
		},
		/**
		 * Container for sections of data.
		 */
		sections: {
			userdata: false,
			userpermissions: false,
			usergroups: false,
			toggle_userdata:function() {
				$scope.AdminContainer.sections.userdata = !$scope.AdminContainer.sections.userdata;
			},
			toggle_userpermissions:function() {
				$scope.AdminContainer.sections.userpermissions = !$scope.AdminContainer.sections.userpermissions;
			},
			toggle_usergroups:function() {
				$scope.AdminContainer.sections.usergroups = !$scope.AdminContainer.sections.usergroups;
			},
		},
	};
	$scope.view = {
		active: null,
	};
	// Init admin view
	$scope.AdminContainer.all_data.get_users();
	$scope.AdminContainer.all_data.get_groups();
	$scope.AdminContainer.all_data.get_permissions();
	if($routeParams.view == null) {
		$scope.view.active = 'user';
	} else {
		$scope.view.active = $routeParams.view;
	}
}]);
