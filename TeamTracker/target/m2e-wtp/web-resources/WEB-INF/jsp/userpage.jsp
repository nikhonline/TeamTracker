<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<meta charset="utf-8" />
<title>Team Tracker</title>
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/lib/bootstrap-3.3.6-dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/css/pageheader.css">
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/lib/font-awesome-4.4.0/css/font-awesome.min.css" />
	<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/lib/angular-datatable-0.5/datatables-1.10.9/jquery.dataTables.min.css">
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/css/calendar.css">

<style>
.content-main-page {
 	width: 80%; 
}
</style>
</head>

<body ng-app="usrApp" ng-init="loading=1;csrf=''">


	<!-- For Router URL -->
	<base href="${pageContext.request.contextPath}/usr">

	<sec:authorize access="isAuthenticated()">
		<sec:authentication property="principal.name" var="username" />
	</sec:authorize>

	<!-- 	Handle Header and Content View -->
	<div class="header-main-page">
		<nav class="navbar  navbar-fixed-top header-clr">
			<div class="container">
				<div class="navbar-header">
					<button aria-controls="navbar" aria-expanded="false"
						data-target="#navbar" data-toggle="collapse"
						class="navbar-toggle collapsed" type="button">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a id="nohover" class="navbar-brand">Team Tracker</a>
				</div>
				<div class="navbar-collapse collapse" id="navbar">
					<ul class="nav navbar-nav">
						<li><a href="usr/home">Home</a></li>

					</ul>
					<div class="nav navbar-nav" dropdown is-open="edit.isopen">
						<li dropdown-toggle ng-disabled="disabled"><a
							ng-class="{'menu-click':edit.isopen}" style="cursor: pointer;">Menu
								<span class="caret"></span>
						</a></li>
						<ul class="dropdown-menu" role="menu"
							aria-labelledby="single-button">
							<li role="menuitem"><a href="usr/leavetracker">Manager Leaves</a></li>
							<li role="menuitem"><a href="usr/shifttracker">Manage Shift Details</a></li>
							<li role="menuitem"><a href="usr/calendar">Mine</a></li>
						</ul>
					</div>

					<ul class="nav navbar-nav navbar-right">
						<li><a id="nohover">Hello ${username}</a></li>
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<li ng-controller="redirectCtrl" ng-click="redirect()"><a
								href="#"><i class="fa fa-external-link"></i>Admin</a></li>
						</sec:authorize>
						<li>
							<!-- 	Handle Logout  --> <c:url value="/logout" var="logoutUrl" />
							<form action="${logoutUrl}" method="post">
								<button type="submit" class="nav navbar-nav header-button">
									<li><a><i class="fa fa-sign-out"></i>Logout</a></li>
								</button>
							</form>
						</li>
					</ul>
				</div>

			</div>
		</nav>

	</div>
	<br>
	<div class="content-main-page">
		<div ng-view></div>
	</div>
	<img class="loading" src="resources/lib/images/ajax-loader.gif"
		ng-show="loading"></img>

	<script type="text/javascript"
		src="${pageContext.servletContext.contextPath}/resources/lib/jquery-2.1.4.js"></script>
	<script type="text/javascript"
		src="${pageContext.servletContext.contextPath}/resources/lib/jquery-ui-1.11.4.js"></script>
	<script type="text/javascript"
		src="${pageContext.servletContext.contextPath}/resources/lib/moment.min-2.11.0.js"></script>
	<script type="text/javascript"
		src="${pageContext.servletContext.contextPath}/resources/lib/bootstrap-3.3.6-dist/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/lib/angular_1.5.0/angular.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/lib/angular_1.5.0/angular-animate.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/lib/angular_1.5.0/angular-route.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/lib/ui-bootstrap-tpls-0.14.3.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/js/usr-router.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/js/usr-controller.js"></script>
</body>
</html>
