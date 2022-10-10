const ITEMS_GET = '/fnet/item';
const COMMENTS_GET = '/fnet/comment';
const UPLOAD = '/fnet/upload';
const COMMENT_DELETE = '/fnet/comment';
const ADD_FOLDER =  '/fnet/item/folder';

const SORT_NAME = 'name';
const SORT_DATE = 'date';

app.controller('itemController', ['$scope', '$rootScope', '$http', '$routeParams', '$timeout', function($scope, $rootScope, $http, $routeParams, $timeout) {
	$scope.item = {
		items: null,
		comments: null,
		name: 'resolve',
		sort_type: 'name',
		show_date: false,
		get_item:function(view_id) {
			$scope.current.view = view_id;
			$http.get(ITEMS_GET + '/' + view_id).then(function(response) {
				if (response.status == 200) {
					$scope.item.items = response.data.items;
					$scope.item.name = response.data.name;
				}
			});
			if (view_id != $scope.current.root_view) {
				$http.get(COMMENTS_GET + '/' + view_id).then(function(response) {
					if (response.status == 200) {
						$scope.item.comments = response.data;
					}
				})
			}
		},
		sort:function(sort_type = '') {
			$scope.item.sort_type = sort_type == '' ? SORT_NAME :sort_type;
			$scope.item.items.sort((a,b) => {
				const ta = $scope.item.sort_type == SORT_DATE ? a.item.timestamp : a.item.name;
				const tb = $scope.item.sort_type == SORT_DATE ? b.item.timestamp : b.item.name;
				if (ta > tb) {
					return 1;
				} else if (ta < tb) {
					return -1;
				} else {
					return 0;
				}
			});
		},
	},
	$scope.folder = {
		visible: false,
		show:function() {
			$scope.folder.visible = true;
		},
		hide:function() {
			$scope.folder.visible = false;
			document.getElementById('add-folder').value = '';
		},
		add:function() {
			var folder_name = document.getElementById('add-folder').value;
			var data = {
				"parentId" : $scope.current.view,
				"itemName" : folder_name
			};
			$http.post(ADD_FOLDER, data).then(function(response) {
				if (response.status == 200) {
					$scope.item.items.push(response.data);
					$scope.item.sort();
					$rootScope.information.show('Added folder ' + response.data.item.name);
				} else {
					$rootScope.errorDialog.show('folder not created', 'code: ' + response.status);
				}
			});
			document.getElementById('add-folder').value ='';
			$scope.folder.hide();
		}
	},
	$scope.upload = {
		visible: false,
		in_progress: false,
		show: function() {
			$scope.upload.visible = true;
		},
		hide:function() {
			$scope.upload.visible = false;
			document.getElementById('file').value ='';
		},
		submit:function() {
			$scope.upload.in_progress = true;
			var file = document.getElementById('file').files[0];
			if (file == null) {
				alert('Choose file!');
				return;
			}
			var data = new FormData();
			data.append('file', file);
			data.append('parentId', $scope.current.view);
			$http.put(UPLOAD, data, {
				headers: { "Content-Type" : undefined },
				transformRequest: angular.identity,
				uploadEventHandlers: {
					progress:function(event) {
						var progress = Math.round(event.loaded / event.total * 100.0);
						document.getElementById('progressbar').style.width = progress + '%';
					}
				}
			})
			.then(function(response) {
				$scope.upload.in_progres = false;
				$scope.upload.visible = false;
				if (response.status == 200) {
					var itemdto = {'item' : response.data};
					$scope.item.items.push(itemdto);
					$scope.latest_container.data.numberOfItems++;
					$scope.item.sort();
					$rootScope.information.show('Uploaded ' + file.name);
				}
			},
			function(error) {
				$scope.upload.in_progress = false;
				$scope.upload.visible = false;
			});
		}
	},
	$scope.comment = {
		visible: false,
		show: function() {
			$scope.comment.visible = true;
		},
		hide:function() {
			$scope.comment.visible = false;
			document.getElementById('comment').value ='';
		},
		add_comment:function() {
			var commentElement = document.getElementById('comment');
			console.log('comment: ' + comment.value);
			var data = {
				"parentId" : $scope.current.view,
				"comment" : comment.value
			};
			$http.put(COMMENT_ADD, data).then(function(response) {
				if (response.status == 200) {
					var comment = response.data;
					$scope.item.comments.push(comment);
				}
				$scope.comment.visible = false;
				$rootScope.information.show('Added comment: ' + comment.comment);
				comment.value = '';
			});
		},
		delete:function(comment_id) {
			$http.delete(COMMENT_DELETE +'/' + comment_id).then(function(response) {
				if (response.status == 200) {
					// find and delete comment
					$scope.item.comments = $scope.item.comments.filter(function(value, index, arr) {
						return value.commentId != comment_id;
					});
					$rootScope.information.show('Comment deleted');
				}
			});
		}
	},
	$scope.item.get_item($scope.current.view);
}]);