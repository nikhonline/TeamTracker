//All controllers
adminApp.controller('ItemsModalCtrl', [ '$rootScope','$scope', '$modalInstance', '$http',
		'categories', 'sharedProperties', 'item', 'isNew', 'url',
		ItemsModalCtrlFn ]);
adminApp.controller('EditUsersCtrl', [ '$rootScope','$scope', '$modal', '$http',
		'$location', 'sharedProperties', 'DTOptionsBuilder',
		'DTColumnDefBuilder', EditUsersCtrlFn ]);
adminApp.controller('redirectCtrl', ['$scope','$window', redirectCtrlFn ]);
adminApp.controller('EditAppDataCtrl', [ '$rootScope','$scope', '$modal', '$http',
		'sharedProperties', 'DTOptionsBuilder', 'DTColumnDefBuilder',
		EditAppDataCtrlFn ]);
adminApp.controller('EditManagerCtrl', [ '$rootScope','$scope', '$modal', '$http',
		'sharedProperties', 'DTOptionsBuilder', 'DTColumnDefBuilder',
		EditManagerCtrlFn ]);

//All Controller Functions

function ItemsModalCtrlFn($rootScope,$scope, $modalInstance, $http, categories,
		sharedProperties, item, isNew, url) {
	$scope.categories = categories;
	$scope.archived = sharedProperties.getArchived();
	$scope.item = angular.copy(item);
	$scope.isNew = isNew;

	$scope.reset = function() {
		$scope.item = angular.copy(item);
	}

	$scope.ok = function() {
		
		$rootScope.loading = 1;
		if (isNew) {
			$http
					.post(url, $scope.item)
					.success(function(response) {
						$rootScope.loading--;
						alert("Operation was Successfully done");
						angular.copy($scope.item, item);
						$modalInstance.close(true);
					})
					.error(
							function(response) {
								$rootScope.loading--;
								alert("Operation was failed! Please check the entries or try again later");
							});
		} else {
			$http
					.put(url, $scope.item)
					.success(function(response) {
						$rootScope.loading--;
						alert("Operation was Successfully done");
						angular.copy($scope.item, item);
						$modalInstance.close(true);
					})
					.error(
							function(response) {
								$rootScope.loading--;
								alert("Operation was failed! Please check the entries or try again later");
							});
		}

	};

	$scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	};

}

function EditUsersCtrlFn($rootScope,$scope, $modal, $http, $location, sharedProperties,
		DTOptionsBuilder, DTColumnDefBuilder) {
	$scope.users = [];
	$scope.archived = sharedProperties.getArchived();
	$scope.dtOptions = sharedProperties.getDtOptions();
	$scope.dtColumnDefs = sharedProperties.getDtColumnDefs();

	$scope.restCall = function() {
		$rootScope.loading=1;
		$http.get("data/users").success(function(response) {
			$scope.users = response;
			$rootScope.loading--;
		}).error(function(data) {
			$rootScope.loading--;
		});
	}

	$scope.init = function(value) {
		$scope.restCall();
	}
	$scope.open = function(item, isNew, templateUrl) {

		var modalInstance = $modal.open({
			animation : $scope.animationsEnabled,
			templateUrl : templateUrl || 'myModalContent.html',
			controller : 'ItemsModalCtrl',
			resolve : {
				item : function() {
					return item;
				},
				categories : function() {
					// instead of using new object
					// re-using the existing structure
					return sharedProperties.getRoles();
				},
				sharedProperties : function() {
					return sharedProperties;
				},
				isNew : function() {
					return isNew;
				},
				url : function() {
					return "data/users";
				}
			}
		});
		modalInstance.result.then(function(success) {
			if (success && isNew) {
				$scope.users.push(item);
			} else {
				console.log('falied to push user', success, isNew);
			}
		});

	};

	$scope.addNewUser = function() {
		var item = {
			"userId" : "",
			"name" : "",
			"active" : true,
			"role" : 'USER'
		};
		var success = $scope.open(item, true, 'newUserContent.html');
	}

	$scope.deleteUser = function(item) {
		$rootScope.loading = 1;
		var isSuccess=true;
		$http({
			method : 'DELETE',
			url : 'data/users/' + item.userId
		}).then(function successCallback(response) {
			$rootScope.loading--;
			alert("User deleted successfully");
			item.active=!item.active;
		}, function errorCallback(response) {
			$rootScope.loading--;
			alert("Error while deleting the user try it sometime later");
			isSuccess= true;
		});
		return isSuccess;
	}

}

function redirectCtrlFn($scope,$window) {
	$scope.redirect = function() {
		// user mode
		$window.open('ui/home', '_blank');
	};
}

function EditAppDataCtrlFn($rootScope,$scope, $modal, $http, sharedProperties,
		DTOptionsBuilder, DTColumnDefBuilder) {
	$scope.appConfigData = sharedProperties.getAppConfigData();
	$scope.appdata = [];
	$scope.dtOptions = sharedProperties.getDtOptions();
	$scope.dtColumnDefs = sharedProperties.getDtColumnDefs();

	$scope.restCall = function() {
		$rootScope.loading = 1;
		
		$scope.appConfigData.push({
			id : 'PN',
			name : "Project Name"
		});
		
		$http.get("data/appdata").success(function(response) {
			$scope.appdata = response;
			$rootScope.loading--;
		}).error(function(data) {
			$rootScope.loading--;
		});
	}

	$scope.init = function(value) {
		$scope.restCall();
	}
	$scope.open = function(item, isNew) {

		var modalInstance = $modal.open({
			animation : $scope.animationsEnabled,
			templateUrl : 'myModalContent.html',
			controller : 'ItemsModalCtrl',
			resolve : {
				item : function() {
					return item;
				},
				categories : function() {
					// instead of using new object
					// re-using the existing structure
					return $scope.appConfigData;
				},
				sharedProperties : function() {
					return sharedProperties;
				},
				isNew : function() {
					return isNew;
				},
				url : function() {
					return "data/appdata";
				}
			}
		});
		modalInstance.result.then(function(success) {
			if (success && isNew) {
				$scope.appdata.push(item);
			} else {
				console.log('falied to push user', success, isNew);
			}
		});

	};

	$scope.addNewData = function() {
		var item = {
			"ID" : "",
			"VALUE" : ""
		};
		var success = $scope.open(item, true);
	}

}

function EditManagerCtrlFn($rootScope,$scope, $modal, $http, sharedProperties,
		DTOptionsBuilder, DTColumnDefBuilder) {
	$scope.managers = [];
	$scope.projects = [];
	$scope.isopen = false;
	$scope.archived = sharedProperties.getArchived();
	$scope.dtOptions = sharedProperties.getDtOptions();
	$scope.dtColumnDefs = sharedProperties.getDtColumnDefs();

	$scope.restCall = function() {
		$rootScope.loading = 2;
		
		$http.get("data/projects", {
			params : {
				all : 1
			}
		}).success(function(response) {
			$scope.projects = response;
			$rootScope.loading--;
		}).error(function(data) {
			$rootScope.loading--;
		});

		$http.get("data/managermapping").success(function(response) {
			$scope.managers = response;
			$rootScope.loading--;
		}).error(function(data) {
			$rootScope.loading--;
		});
	}

	$scope.init = function(value) {
		$scope.restCall();
	}
	$scope.open = function(item, isNew) {

		var modalInstance = $modal.open({
			templateUrl : 'myModalContent.html',
			controller : 'ItemsModalCtrl',
			resolve : {
				item : function() {
					return item;
				},
				categories : function() {
					// instead of using new object
					// re-using the existing structure
					return $scope.projects;
				},
				sharedProperties : function() {
					return sharedProperties;
				},
				isNew : function() {
					return isNew;
				},
				url : function() {
					return "data/managers";
				}
			}
		});
		modalInstance.result.then(function(success) {
			if (success && isNew) {
				$scope.managers.push(item);
			} else {
				console.log('falied to push user', success, isNew);
			}
		});

	};
	
	$scope.deleteManager = function(item) {
		$rootScope.loading = 1;
		var isSuccess=true;
		$http({
			method : 'DELETE',
			url : 'data/managers/' + item.managerId
		}).then(function successCallback(response) {
			$rootScope.loading--;
			alert("Item deleted successfully");
			item.active=!item.active;
		}, function errorCallback(response) {
			$rootScope.loading--;
			alert("Error while deleting the item try it sometime later");
			isSuccess= true;
		});
		return isSuccess;
	}

	$scope.addNewData = function() {
		var item = {
			"managerId" : "",
			"name" : "",
			"email" : "",
			"active" : true,
			"projectIds" : []
		};
		var success = $scope.open(item, true);
	}

}