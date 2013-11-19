<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<title>City</title>
</head>
<body>
<h1>
	Add a new city
</h1>
	<form:form modelAttribute="city" method="POST" action='/add'>
		<label for="name">Name:</label>
		<form:input id="name" path="city" value="" />
		<br />
		<label for="country">Country:</label>
		<form:input id="country" path="country" value="" />
		<br />
		<label for="population">Population:</label>
		<form:input id="population" path="population" value="" />
		<br />
		<input id="submitNewCity" type="submit" value="Add this city" />
	</form:form>
</body>
</html>
