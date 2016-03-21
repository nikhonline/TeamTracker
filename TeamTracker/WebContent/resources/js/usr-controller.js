usrApp.controller('redirectCtrl', [ '$scope', '$window', redirectCtrlFn ]);
usrApp.controller('MyLeaveTrackerCtrl', [ '$rootScope', '$scope', '$modal',
		'$http', '$filter', MyLeaveTrackerCtrlFn ]);
usrApp.controller('ItemsModalCtrl', [ '$scope', '$modalInstance', 'events',
		'categories', 'start', 'end', ItemsModalCtrlFn ]);
usrApp.directive('draggable', [ '$compile', draggableFn ]);
usrApp.directive('droppable', [ '$compile', droppableFn ]);
usrApp.filter('keylength', keyLengthFn);
/**
 * Helps to calculate selected Leave Categories length 
 * @returns {Function}
 */
function keyLengthFn() {
	return function(input,id) {
		var length =0;
		if(id){
			//if any specific id given
			angular.forEach(input, function(value, key){
				if(value.id ===id){
					length++;
				}
			});
		}else{
			angular.forEach(input, function(value, key){
				length++;
			});
		}
		
		return length;
	}
}

function MyLeaveTrackerCtrlFn($rootScope, $scope, $modal, $http,$filter) {
	$scope.categories = [ {
		id : 'T',
		title : 'Tentative Leave'
	}, {
		id : 'L',
		title : 'Approved Leave'
	} ];
	$scope.weeks = [ 'Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat' ];
	$scope.months = [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug','Sep', 'Oct', 'Nov', 'Dec' ];
	$scope.holidays = {
		1 : {
			26 : 'Republic day',
			15 : 'Makara Sankranti'
		},
		2 : {
			2 : 'Holiday 3',
			13 : 'Holiday 4'
		},
		3:{
			25:'Good Friday'
		}
	};
	$scope.allYrevents=[{
						mid : '1031956',
						name : 'Kavya H 1',
						1:{1 : {id : 'T'},
						7 : {id : 'L'},13 : {id : 'L'},14 : {id : 'L'},21 : {id : 'L'},28 : {id : 'L'},31 : {id : 'T'}},
						12:{7 : {id : 'T'}},
						4:{7 : {id : 'L'}}},
						{
							mid : '1031957',
							name : 'Kavya H 2',
							1:{1 : {id : 'T'},
								7 : {id : 'L'},13 : {id : 'L'},14 : {id : 'L'},21 : {id : 'L'},28 : {id : 'L'},31 : {id : 'T'}},
							3:{7 : {id : 'T'}},
							4:{7 : {id : 'L'}}}];
	$scope.events;
	$scope.yrdata = [ {
		mid : '1031956',
		name : 'Kavya H',
		leaves : [ 5, 1, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0 ]
	} ];
	
	$scope.change = {
		format : 'MMMM-yyyy',
		dateOpened : false,
		date : moment().add(-20, 'd'),
		weeks : 0,
		month:0
	};

	$scope.days = {
		start : '',
		end : ''
	};

	$scope.removeEvent = function(index) {
		delete $scope.events[index];
		$scope.mouseIsDown = false;
	}

	$scope.mouseDown = function(day) {
		$scope.mouseIsDown = true;
		$scope.days.start = parseInt(day);
	}

	$scope.mouseUp = function(day) {
		if ($scope.mouseIsDown !== 'undefined' && $scope.mouseIsDown) {
			$scope.mouseIsDown = false;
			$scope.days.end = parseInt(day);
			if($scope.days.end !=$scope.days.start){
				$scope.open();
			}
			
		}

	}
	$scope.open = function() {

		var modalInstance = $modal.open({
			size : 'sm',
			templateUrl : 'myModalContent.html',
			controller : 'ItemsModalCtrl',
			resolve : {
				start : function() {
					return $scope.days.start;
				},
				end : function() {
					return $scope.days.end;
				},
				categories : function() {
					return $scope.categories;
				},
				events : function() {
					return $scope.events;
				}
			}
		});
		$scope.days.start = '';
		$scope.days.end = '';

	};
	$scope.init = function() {
		$rootScope.loading = 0;
		$scope.getWeeks();
	}

	$scope.range = function(total) {
		total = parseInt(total);
		var input = []

		for (var i = 0; i < total; i++) {
			input.push(i);
		}
		return input;
	}

	function sameMonth(a, b, other) {
		if (a.month() !== b.month()) {
			return other;
		}
		return a.date();
	}

	$scope.getWeeks = function() {
		//get the weeks available in the month
		var m= $scope.change.date;
		
		var lastOfMonth = m.clone().endOf('month'), lastOfMonthDate = lastOfMonth
				.date(), firstOfMonth = m.clone().startOf('month'), currentWeek = firstOfMonth
				.clone().day(0), output = [], startOfWeek, endOfWeek;
		while (currentWeek < lastOfMonth) {
			startOfWeek = sameMonth(currentWeek.clone().day(0), firstOfMonth, 1);
			endOfWeek = sameMonth(currentWeek.clone().day(6), firstOfMonth,
					lastOfMonthDate);
			var week = [];
			for (i = startOfWeek; i <= endOfWeek; i++) {
				week.push('' + i);
			}
			output.push(week);
			currentWeek.add('d', 7);
		}

		$scope.change.month=m.month()+1;
		
		var data;
		if($scope.allYrevents.length ==1){
			data=$scope.allYrevents;
		}else{
			data=$filter('filter')($scope.allYrevents,{mid: '1031956' });
		}
		if(data.length>0){
			$scope.events = data[0][$scope.change.month]
		}else{
			$scope.events = {};
		}
		$scope.change.weeks = output;

	}

}

function ItemsModalCtrlFn($scope, $modalInstance, events, categories, start,
		end) {
	$scope.categories = categories;
	$scope.item;

	$scope.ok = function() {
		var item = angular.copy($scope.item);
		if(start > end){
			//check left select  
			var temp = start;
			start=end;
			end=temp;
		}
		for (; start <= end; start++) {
			events[start] = {
				"id" : item.id
			};
		}
		
		$modalInstance.close(true);

	};
}
function redirectCtrlFn($scope, $window) {
	$scope.redirect = function() {
		// admin mode
		$window.open('admin/home', '_blank');
	};
}
function droppableFn($compile) {
	return {
		restrict : 'A',
		link : function(scope, element, attrs) {
			element.droppable({
				drop : function(event, ui) {
					var id = scope.$eval(ui.draggable.attr('ng-model'));
					var day = scope.$eval(attrs.ngModel);
					if (day !== undefined) {
						scope.$apply(function() {
							scope.events[day] = {
								id : id
							};
						});
					}

				}

			});

		}
	}
}
function draggableFn($compile) {
	return {
		restrict : 'A',
		link : function(scope, element, attrs) {

			// make the event draggable using jQuery UI
			element.draggable({
				zIndex : 999,
				opacity : 0.7,
				cursor : 'move',
				helper : "clone",
				revertDuration : 0
			});
		}
	}
}
