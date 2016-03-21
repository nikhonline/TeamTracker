var usrApp = angular.module("usrApp", [ 'ngAnimate', 'ngRoute', 'ui.bootstrap' ]);

usrApp.config(function($routeProvider, $locationProvider) {
	// setting base url with router
	$locationProvider.html5Mode(true);

	$routeProvider.when('/usr/leavetracker', {
		templateUrl : './resources/templates/leaveTracker.htm',
		controller : 'LeaveTrackerCtrl',
		resolve : {
			isShift : function() {
				return false;
			}
		}
	}).when('/usr/shifttracker', {
		templateUrl : './resources/templates/leaveTracker.htm',
		controller : 'LeaveTrackerCtrl',
		resolve : {
			isShift : function() {
				return true;
			}
		}
	}).when('/usr/calendar', {
		templateUrl : './resources/templates/myLeaveTracker.htm',
		controller : 'MyLeaveTrackerCtrl',
		
	}).otherwise({
		redirectTo : '/usr/calendar'
	});
});
usrApp.config([ '$httpProvider', function($httpProvider) {
	// setting common http defaults
	$httpProvider.defaults.headers.common['Accept'] = 'application/json';
	$httpProvider.defaults.headers.common['Content-Type'] = 'application/json';
} ]);