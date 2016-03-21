<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<head>
<meta charset="utf-8">
<title>Login Page</title>
<link
	href="${pageContext.servletContext.contextPath}/resources/lib/bootstrap-3.3.6-dist/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/lib/font-awesome-4.4.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/css/login.css">

<style>
#divCentered{
    position: absolute;
    top: 20%;
    left: 10%;
    width: 80%;
}
</style>

</head>
<body onload='document.loginform.username.focus();'>

	<div id='divCentered' >
		<div class="col-md-4 col-md-offset-4">
			<div class="panel panel-success">
				<div class="panel-heading">
					<div class="panel-title">Please sign in</div>
				</div>
				<span class="help-block"></span><span class="help-block"></span>
				<div class="panel-body ">
					<c:if test="${not empty error}">
						<p class="error">${error}</p>
					</c:if>
					<c:if test="${not empty msg}">
						<p class="msg">${msg}</p>
					</c:if>

					<form name='loginform' action="<c:url value='/login' />" method='POST'>
						<fieldset>
							<div class="input-group">
								<span class="input-group-addon"> <i class="fa fa-user"></i>
								</span> <input type="text" name="username" placeholder="username"
									class="form-control" required>
							</div>
							<span class="help-block"></span>
							<div class="input-group">
								<span class="input-group-addon"> <i class="fa fa-lock"></i>
								</span> <input type="password" value="" name="password"
									placeholder="Password" class="form-control" required>
							</div>
							<span class="help-block"></span> <button type="submit"
								class="btn btn-lg btn-success btn-block"> <i class="fa fa-sign-in"></i>	Login</button>
						</fieldset>
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>