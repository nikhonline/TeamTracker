srMgrApp.controller('redirectCtrl', [ '$scope', '$window', redirectCtrlFn ]);
srMgrApp.controller('ViewSrMgrCtrl', [ '$rootScope', '$scope', '$filter',
		'$timeout', '$http', 'sharedProperties', ViewSrMgrCtrlFn ]);

function redirectCtrlFn($scope, $window) {
	$scope.redirect = function() {
		// admin mode
		$window.open('admin/home', '_blank');
	};
}
function ViewSrMgrCtrlFn($rootScope, $scope, $filter, $timeout, $http,
		sharedProperties) {
	$scope.activeMethod;
	$scope.chartTypes = [ 'Line', 'Bar', 'Areaspline', 'Column' ];
	$scope.chartType = $scope.chartTypes[0];
	$scope.chartTypeChange = function(chartType) {
		$scope.chartConfig.options.chart.type = chartType.toLowerCase();
	}
	$scope.chartConfig = {
		options : {
			chart : {
				backgroundColor : '#FCFFC5',
				type : 'bar',
			}
		},
		series : [ {
			name : 'Team 1',
			data : [ 10, 15, 12, 8, 7, 1, 1, 19, 15, 10 ]
		}, {
			name : 'Team 2',
			data : [ 15, 12, 8, 7, 1, 1, 19, 15, 10, 21 ]
		} ],
		title : {
			text : 'Team Trend'
		},
		credits : {
			"enabled" : false
		},
		loading : false
	};
	
	$scope.ChartChanged = function(){
		
	}
	$scope.init = function() {
		$rootScope.loading = 0;
		$scope.chartTypeChange($scope.chartType);
	}
}
