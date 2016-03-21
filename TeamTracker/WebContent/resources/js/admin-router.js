var adminApp = angular.module("adminApp", [ 'ngAnimate', 'ngRoute',
		'ui.bootstrap', 'datatables', 'datatables.tabletools' ]);

adminApp.service('sharedProperties', [
		'DTOptionsBuilder',
		'DTColumnDefBuilder',
		function(DTOptionsBuilder, DTColumnDefBuilder) {
			var archived = [ {
				id : true,
				name : 'Yes'
			}, {
				id : false,
				name : 'No'
			} ];
			var roles = [ {
				id : 'MANAGER',
				name : 'Manager'
			}, {
				id : 'ADMIN',
				name : 'Admin'
			} ];
			var appConfig = [ {
				id : 'MM',
				name : 'Matrix Manager'
			}, {
				id : 'GH',
				name : 'Group Head'
			}, {
				id : 'TCL',
				name : 'Tech Cabinet Lead'
			}, {
				id : 'AA',
				name : 'AOL Area'
			}, {
				id : 'CC',
				name : 'Cost Center'
			}, {
				id : 'TM',
				name : 'Team Name'
			}, {
				id : 'PR',
				name : 'Project Role'
			}, {
				id : 'PM',
				name : 'Project Model'
			}, {
				id : 'WS',
				name : 'Working Status'
			} ];
			var dtOptions = DTOptionsBuilder.newOptions().withPaginationType(
					'full_numbers').withTableTools(
					'resources/lib/angular-datatable-0.5/copy_csv_xls_pdf.swf');
			var dtColumnDefs = [ DTColumnDefBuilder.newColumnDef(0),
					     			DTColumnDefBuilder.newColumnDef(1)];

			return {
				getArchived : function() {
					return archived;
				},
				getRoles : function() {
					return roles;
				},
				getDtOptions : function() {
					return dtOptions;
				},
				getDtColumnDefs : function() {
					return dtColumnDefs;
				},
				getAppConfigData : function() {
					return appConfig;
				}
			};

		} ]);

adminApp.config(function($routeProvider, $locationProvider) {
	// setting base url with router
	$locationProvider.html5Mode(true);

	$routeProvider.when('/admin/editusers', {
		templateUrl : './resources/templates/editUsers.htm',
		controller : 'EditUsersCtrl'
	}).when('/admin/editappdata', {
		templateUrl : './resources/templates/editAppData.htm',
		controller : 'EditAppDataCtrl'
	}).when('/admin/editmanagers', {
		templateUrl : './resources/templates/editManagers.htm',
		controller : 'EditManagerCtrl'
	}).otherwise({
		redirectTo : '/admin/editusers'
	});
});
adminApp.config([ '$httpProvider', function($httpProvider) {
	// setting common http defaults
	$httpProvider.defaults.headers.common['Accept'] = 'application/json';
	$httpProvider.defaults.headers.common['Content-Type'] = 'application/json';
} ]);