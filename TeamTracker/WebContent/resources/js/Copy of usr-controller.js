usrApp.controller('redirectCtrl', [ '$scope', '$window', redirectCtrlFn ]);
usrApp.controller('LeaveTrackerCtrl',
		[ '$rootScope', '$scope', '$modal', '$http', '$compile',
				'uiCalendarConfig', 'isShift', LeaveTrackerCtrlFn ]);
usrApp.controller('MyLeaveTrackerCtrl', [ '$rootScope', '$scope', '$modal',
		'$http', '$compile', MyLeaveTrackerCtrlFn ]);
usrApp.controller('ItemsModalCtrl', [ '$scope', '$modalInstance', 'events',
		'categories', 'start', 'end', ItemsModalCtrlFn ]);
usrApp.directive('draggable', [ '$compile', draggableFn ]);
usrApp.directive('droppable', [ '$compile', droppableFn ]);


function MyLeaveTrackerCtrlFn($rootScope, $scope, $modal, $http) {
	$scope.categories = [ {
		id : 'T',
		title : 'Tentative Leave',
		color : '#ff9900'
	}, {
		id : 'L',
		title : 'Approved Leave',
		color : 'green'
	} ];
	
	$scope.events= {1 : [{id:'Tentative Leave'}],13 : [{id:'Approved Leave'}],31 : [{id:'Tentative Leave'}]};//[{1 : {id:'T'}},{13 : {id:'A'}},{31 : {id:'T'}}];
	$scope.change = {
		format : 'MMMM-yyyy',
		dateOpened : false,
		date : moment().add(-20, 'd')
	};
	$scope.args = {
		sdate : $scope.change.date.clone().startOf('month').add(1, 'd'),
		edate : $scope.change.date.clone().endOf('month'),
		weeks : 0
	};
	$scope.days={start:'',end:''};
	
	$scope.remove = function(index){
		delete $scope.events[index];
	}

	$scope.drawImage = function(day) {
		if ($scope.mouseIsDown) {
			console.log(day);
		}
	}

	$scope.mouseDown = function(day) {
		$scope.mouseIsDown = true;
		$scope.days.start=day;
	}

	$scope.mouseUp = function(day) {
		$scope.mouseIsDown = false;
		$scope.days.end=day;
		$scope.open();
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

	};
	$scope.init = function() {
		$rootScope.loading = 0;
		$scope.args.weeks = weeks($scope.args.edate);
//		$( "#calendar1" ).droppable({
//			  drop: function() {
//			    alert( "dropped" );
//			  }
//			});
	}

	$scope.range = function( total) {
		total = parseInt(total);
		var input =[]

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

	function weeks(m) {
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

		return output;
	}
	$scope.addEvent = function(item){
		console.log(item);
		console.log($scope.events);
		$scope.events[item.id]=item.value;
		console.log($scope.events);
	}

}

function LeaveTrackerCtrlFn($rootScope, $scope, $modal, $http, $compile,
		uiCalendarConfig, isShift) {
	$scope.isShift = isShift;
	$scope.categories;
	var date = new Date();
	var d = date.getDate();
	var m = date.getMonth();
	var y = date.getFullYear();

	/* event source that pulls from google.com */
	$scope.holidays = [ {
		id : 'H',
		title : 'Sankranti',
		start : new Date(2016, 0, 15),
		allDay : true,
		color : '#4ce600'
	}, {
		id : 'H',
		title : 'Independence day',
		start : new Date(2016, 0, 26),
		allDay : true,
		color : '#4ce600'
	} ];
	/* event source that contains custom events on the scope */
	$scope.events = [];

	function allDayEvents(date) {
		var allevents = false;
		$scope.events.forEach(function(entry) {
			if (!allevents) {
				if (moment(date).isSame(entry['start'], 'day')) {
					allevents = true;
				} else if (moment(date).isBetween(entry['start'], entry['end'],
						'day')) {
					allevents = true;
				}
			}
		});

		return allevents;
	}

	/* config object */
	$scope.uiConfig = {
		calendar : {
			header : {
				left : 'prev',
				center : 'title',
				right : 'today next'
			},
			height : 500,
			overlap : false,
			selectable : true,
			selectHelper : true,
			select : onSelectDay,
			editable : true,
			dayNamesShort : [ "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" ],
			dayClick : function(date, allDay, jsEvent, view) {
				var val = allDayEvents(date._d);
				console.log(val);
			},
			eventOverlap : function(stillEvent, movingEvent) {
				if (stillEvent.id === 'T' || stillEvent.id === 'L') {
					return false;
				}
				return true;
			},
			droppable : true,
			drop : function(date) {
				// external button drop
				if (date.id === 'H') {
					return;
				}
				var val = allDayEvents(date._d);
				console.log(val);
			},
			eventDrop : function(event, delta, revertFunc) {
				// internal button drop
				// ignore holidays movement
				if (event.id === 'H') {
					revertFunc();
				}

				var val = allDayEvents(date._d);
				console.log(val);
			},
			eventDragStop : function(event, jsEvent) {
				if (event.id === 'H') {
					return;
				}
				var calendar = $('#calendar');
				var ofs = calendar.offset();

				var x1 = ofs.left;
				var x2 = x1 + calendar.outerWidth(true);
				var y1 = ofs.top;
				var y2 = y1 + calendar.outerHeight(true);
				if (!(x1 < jsEvent.pageX && jsEvent.pageX < x2
						&& y1 < jsEvent.pageY && jsEvent.pageY < y2)) {
					calendar.fullCalendar('removeEvents', event._id);
				}
			}
		}
	};

	$scope.restCall = function() {
		$rootScope.loading = 0;
	}

	$scope.init = function() {
		if (isShift) {
			$scope.categories = [ {
				id : 'S1',
				title : 'S1',
				color : '#ff9900'
			}, {
				id : 'S2',
				title : 'S2',
				color : 'green'
			} ];
			$scope.events = [ {
				id : 'S1',
				title : 'S1',
				start : new Date(y, m, 1),
				allDay : true,
				color : '#ff9900'
			}, {
				id : 'S2',
				title : 'S2',
				start : new Date(y, m, d - 5),
				end : new Date(y, m, d - 2),
				allDay : true,
				color : 'green'
			} ];
			$scope.holidays.push({
				id : 'L',
				title : 'Approved Leave',
				start : new Date(y, m, d - 5),
				end : new Date(y, m, d - 2),
				allDay : true,
				color : '#4ce600',
				rendering : 'background'
			}, {
				id : 'L',
				title : 'Approved Leave',
				start : new Date(y, m, 12),
				allDay : true,
				color : '#4ce600',
				rendering : 'background'
			});
		} else {
			$scope.categories = [ {
				id : 'T',
				title : 'Tentative Leave',
				color : '#ff9900'
			}, {
				id : 'L',
				title : 'Approved Leave',
				color : 'green'
			} ];
			$scope.events = [ {
				id : 'T',
				title : 'Tentative Leave',
				start : new Date(y, m, 1),
				allDay : true,
				color : '#ff9900',
			}, {
				id : 'L',
				title : 'Approved Leave',
				start : new Date(y, m, d - 5),
				end : new Date(y, m, d - 2),
				allDay : true,
				color : 'green',
			}, {
				id : 'L',
				title : 'Approved Leave',
				start : new Date(y, m, d + 7),
				allDay : true,
				color : 'green',
			}, {
				id : 'T',
				title : 'Tentative Leave',
				start : new Date(y, m, d + 4),
				allDay : true,
				color : '#ff9900',
			} ]
		}

		$scope.eventSources = [ $scope.events, $scope.holidays ];
		$scope.restCall();

	}
	$scope.open = function(start, end) {

		var modalInstance = $modal.open({
			size : 'sm',
			templateUrl : 'myModalContent.html',
			controller : 'ItemsModalCtrl',
			resolve : {
				start : function() {
					return start;
				},
				end : function() {
					return end;
				},
				categories : function() {
					return $scope.categories;
				},
				events : function() {
					return $scope.events;
				}
			}
		});

	};

	function onSelectDay(start, end) {
		if (start._d.getMonth() !== end._d.getMonth()) {
			// if not same month then dont add
			return false;
		}
		$scope.open(start, end);

		$('#calendar').fullCalendar('unselect');
	}

}
function ItemsModalCtrlFn($scope, $modalInstance, events, categories, start,
		end) {
	$scope.categories = categories;
	$scope.item;

	$scope.ok = function() {
		var item = angular.copy($scope.item);
		item['start'] = start;
		item['end'] = end;
		events.push(item);
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
					var dropItem = scope.$eval(ui.draggable.attr('ng-bind'));
					var dropItem1 = scope.$eval('event');
					var day = scope.$eval(attrs.ngModel);
					if (day !== undefined) {
						scope.$apply(function() {
							scope.events[day] = [ {
								id : dropItem
							} ];
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
				opacity: 0.7,
				cursor: 'move',
//				revert:true,
				helper: "clone",
				revertDuration : 0
			});
		}
	}
}
