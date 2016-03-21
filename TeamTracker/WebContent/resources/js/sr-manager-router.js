var srMgrApp = angular.module("srMgrApp", [ 'ngAnimate', 'ngRoute',
		'ui.bootstrap', 'datatables', 'datatables.tabletools', 'highcharts-ng' ]);

srMgrApp.service('sharedProperties', [
		'$http',
		'DTOptionsBuilder',
		'DTColumnDefBuilder',
		function($http, DTOptionsBuilder, DTColumnDefBuilder) {
			var yesOrNo = [ {
				id : true,
				name : 'Yes'
			}, {
				id : false,
				name : 'No'
			} ];
			var dtOptions = DTOptionsBuilder.newOptions().withPaginationType(
			'full_numbers').withTableTools(
			'resources/lib/angular-datatable-0.5/copy_csv_xls.swf');
			var dtColumnDefs = [ DTColumnDefBuilder.newColumnDef(0),
					DTColumnDefBuilder.newColumnDef(1) ];

			return {
				getYesOrNo : function() {
					return yesOrNo;
				},
				getDtOptions : function() {
					return dtOptions;
				},
				getDtColumnDefs : function() {
					return dtColumnDefs;
				}
			};
		} ]);

srMgrApp.config(function($routeProvider, $locationProvider) {
	// setting base url with router
	$locationProvider.html5Mode(true);

	$routeProvider.when('/srmgr/view', {
		templateUrl : './resources/templates/viewSrMgr.htm',
		controller : 'ViewSrMgrCtrl'
	}).otherwise({
		redirectTo : '/srmgr/view'
	});
});
srMgrApp.config([ '$httpProvider', function($httpProvider) {
	// setting common http defaults
	$httpProvider.defaults.headers.common['Accept'] = 'application/json';
	$httpProvider.defaults.headers.common['Content-Type'] = 'application/json';
} ]);
srMgrApp.run(function(DTDefaultOptions) {
	// Display 5 items per page by default
	DTDefaultOptions.setDisplayLength(5);
});