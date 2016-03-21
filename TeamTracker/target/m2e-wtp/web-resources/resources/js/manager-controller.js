mainApp.controller('redirectCtrl', [ '$scope', '$window', redirectCtrlFn ]);
mainApp.controller('ChangePwdCtrl', [ '$rootScope', '$scope', '$filter',
		'$timeout', '$http', 'sharedProperties', ChangePwdCtrlFn ]);
mainApp.controller('ItemsModalCtrl', [ '$rootScope', '$scope',
		'$modalInstance', '$http', '$filter', 'projects', 'managers',
		'sharedProperties', 'item', 'isNew', 'url', ItemsModalCtrlFn ]);
mainApp.controller('ViewTeamMembersCtrl', [ '$rootScope', '$scope', '$modal',
		'$http', '$filter', 'sharedProperties', 'DTOptionsBuilder',
		'DTColumnDefBuilder', 'history', ViewTeamMembersCtrlFn ]);

function ViewTeamMembersCtrlFn($rootScope, $scope, $modal, $http, $filter,
		sharedProperties, DTOptionsBuilder, DTColumnDefBuilder, history) {
	$scope.users = [];
	var usersMasterData = [];
	$scope.selectedPros = [];
	var projects = [];
	$scope.allProjects = [];
	$scope.managers = [];
	$scope.selectedMgrs = [];
	$scope.yesOrNo = sharedProperties.getYesOrNo();
	$scope.dtOptions = sharedProperties.getDtOptions();
	$scope.dtColumnDefs = sharedProperties.getDtColumnDefs();
	$scope.billable;
	$scope.change = {
		format:'dd-MMM-yyyy',
		sdate : false,
		edate : false
	};
	$scope.queryParam = {
		params : {
			history : history,
			uid : null,
			sdate : new Date(),
			edate:null
		}
	};
	$scope.sdateChange = function() {
		$scope.change.sdate = !$scope.change.sdate;
	}
	$scope.edateChange = function() {
		$scope.change.edate = !$scope.change.edate;
	}

	$scope.$watchGroup([ 'selectedPros+billable+selectedMgrs' ], function() {
		$scope.users = $filter('filter')(
				usersMasterData,
				function(actual) {
					if ($scope.selectedPros.indexOf(actual.projectId) === -1
							|| $scope.selectedMgrs
									.indexOf(actual.mindtreeManager) === -1) {
						return false;
					}
					if ($scope.billable !== 'All') {
						if (actual.billable + "" !== $scope.billable + "") {
							return false;
						}
					}
					return true;
				});
	}, true);
	$scope.deleteTeamMember = function(item, index) {
		$rootScope.loading = 1;
		$http({
			method : 'DELETE',
			url : "data/members/" + item.id
		}).then(function successCallback(response) {
			$rootScope.loading--;
			alert("User Successfully archived");
			$scope.users.splice(index, 1);
		}, function errorCallback(response) {
			$rootScope.loading--;
			alert("Oops! Something went wrong.Please try after some time");
		});
	}
	$scope.doAction = function(action, item, history, index) {
		switch (action) {
		case "1":
			$scope.open(item, history);
			break;
		case "2":
			$scope.addNewTeamMember(item);
			break;
		case "3":
			$scope.deleteTeamMember(item, index);
			break;
		}
	}
	$scope.getTeamData = function() {
		$rootScope.loading++;
		$scope.queryParam.params.sdate = $filter('date')(
				$scope.queryParam.params.sdate, 'yyyy-MM-dd');
		if($scope.queryParam.params.edate!=null){
			$scope.queryParam.params.edate= $filter('date')(
					$scope.queryParam.params.edate, 'yyyy-MM-dd')
		}
		$http.get("data/members", $scope.queryParam).success(
				function(response) {
					$scope.users = usersMasterData = response;
					response.forEach(function(usr) {
						var pid = usr.projectId;
						if ($scope.allProjects.indexOf(pid) === -1) {
							$scope.allProjects.push(pid);
							$scope.selectedPros.push(pid);
						}
						var manager = usr.mindtreeManager;
						if ($scope.managers.indexOf(manager) === -1) {
							$scope.managers.push(manager);
							$scope.selectedMgrs.push(manager);
						}
					});

					$rootScope.loading--;
				}).error(function(data) {
			$rootScope.loading--;
		});
	}
	$scope.restCall = function() {
		$rootScope.loading = 1;
		$http.get("data/projects").success(function(response) {
			projects = response;
			$scope.selectedPros = projects.concat([]);
			$scope.allProjects = projects.concat([]);
			$rootScope.loading--;
		}).error(function(data) {
			$rootScope.loading--;
		});
		$rootScope.loading++;
		$http.get("data/managers").success(function(response) {
			$scope.managers = response;
			$scope.selectedMgrs = response.concat([]);
			$rootScope.loading--;
		}).error(function(data) {
			$rootScope.loading--;
		});

		$scope.getTeamData();
	}

	$scope.reset = function() {
		$scope.queryParam.params.uid = null;
		$scope.restCall();
	}

	$scope.criteriaMatch = function(selected) {
		return function(item) {
			if (selected.length == 0) {
				return true;
			}
			return selected.indexOf(item) == -1 ? false : true;
		};
	};

	$scope.init = function(value) {
		$scope.restCall();
	}
	$scope.open = function(item, isNew) {

		var modalInstance = $modal.open({
			templateUrl : 'myModalContent.html',
			backdrop : 'static',
			windowClass : 'modal-fit',
			controller : 'ItemsModalCtrl',
			resolve : {
				item : function() {
					return item;
				},
				projects : function() {
					return projects;
				},
				managers : function() {
					return $scope.managers;
				},
				sharedProperties : function() {
					return sharedProperties;
				},
				isNew : function() {
					return isNew;
				},
				url : function() {
					return "data/members";
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

	$scope.addNewTeamMember = function(data) {
		var item;
		if (data !== null) {
			item = angular.copy(data);
			item.id = '';
			item.mid = '';
			item.name = '';
			item.pplSftId = '';
			item.monthsOfExp = 0;
			item.endDate = null;
			item.workingStatus='';
		} else {
			item = {
				mid : '',
				name : '',
				pplSftId : '',
				comments : '',
				workingStatus : '',
				billable : true,
				startDate : '',
				endDate : null,
				
				matrixManager : '',
				groupHead : '',
				techCabinetLead : '',
				AOLArea : '',
				costCenter : '',
				team : '',
				projectRole : '',
				projectId : '',
				mindtreeManager : $rootScope.uname,
				projectModel : '',
				monthsOfExp : 0
			};
		}

		var success = $scope.open(item, true);
	}

}
function ItemsModalCtrlFn($rootScope, $scope, $modalInstance, $http, $filter,
		projects, managers, sharedProperties, item, isNew, url) {
	$scope.projects = projects;
	$scope.managers = managers;
	$scope.yesOrNo = sharedProperties.getYesOrNo();
	$scope.appdata = sharedProperties.getAppData();
	$scope.item = angular.copy(item);
	$scope.isNew = isNew;
	$scope.format = 'yyyy-MM-dd';

	$scope.openStartDate = function($event) {
		$scope.status.startDateOpened = !$scope.status.startDateOpened;
	};
	$scope.openEndDate = function($event) {
		$scope.status.endDateOpened = !$scope.status.endDateOpened;
	};
	$scope.updateStartDate = function() {
		$scope.item.startDate = $filter('date')($scope.item.startDate,
				$scope.format);
	}
	$scope.updateEndDate = function() {
		$scope.item.endDate = $filter('date')($scope.item.endDate,
				$scope.format);
	}

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
						if (response === -1) {
							alert("Please check the User existance");
						} else {
							alert("Operation was Successfully done");
							item.id = response;
							angular.copy($scope.item, item);
							$modalInstance.close(true);
						}

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
						if (response) {
							alert("Operation was Successfully done");
							angular.copy($scope.item, item);
							$modalInstance.close(true);
						} else {
							alert("Please check the User existance");
						}
						
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
function redirectCtrlFn($scope, $window) {
	$scope.redirect = function() {
		// admin mode
		$window.open('admin/home', '_blank');
	};
}
function ChangePwdCtrlFn($rootScope, $scope, $filter, $timeout, $http,
		sharedProperties) {
	$scope.oldPwd;
	$scope.newPwd;
	$scope.confirmPwd;
	$rootScope.loading = 0;
	$scope.changePassword = function() {
		if ($scope.newPwd == $scope.confirmPwd) {
			$rootScope.loading = 1;
			$http.put("data/users/" + $scope.newPwd).success(
					function(response) {
						$rootScope.loading--;
						alert("Password Change done successfully.");
					}).error(function(response) {
				$rootScope.loading--;
				alert("Password Change failed.");
			});
		} else {
			alert("Passwords didnt match");
		}
	}
	$scope.reset = function() {
		$scope.oldPwd = '';
		$scope.newPwd = '';
		$scope.confirmPwd = '';
	}
}

mainApp.directive('numericOnly', function() {
	return {
		require : 'ngModel',
		link : function(scope, element, attrs, modelCtrl) {

			modelCtrl.$parsers.push(function(inputValue) {
				var transformedInput = inputValue ? inputValue.replace(
						/[^\d.-]/g, '').replace('-', '') : null;

				if (transformedInput != inputValue) {
					if (transformedInput < 0) {
						transformedInput = "";
					}
					modelCtrl.$setViewValue(transformedInput);
					modelCtrl.$render();
				}

				return transformedInput;
			});
		}
	};
});
