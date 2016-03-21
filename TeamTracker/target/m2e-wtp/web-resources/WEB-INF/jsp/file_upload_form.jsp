<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>

</head>

<body>
	<h2>Spring MVC file upload example</h2>
 	<form:form method="post" action="data/save"
        modelAttribute="uploadForm" enctype="multipart/form-data">
 		Please select a file to upload : <input type="file" name="file" accept=".xls,.xlsx"/>
		<input type="submit" value="upload" />
		<span><form:errors path="file" cssClass="error" />
		</span>

	</form:form>

</body>
</html>