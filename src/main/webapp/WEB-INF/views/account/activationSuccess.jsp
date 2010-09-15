<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<meta  http-equiv="Content-Type"  content="text/html;charset=UTF-8">
<title>Activation Success</title>
</head>
<body>
Wow, it worked. you are activated.
You need to set your password.

<form:form action="/account/activation.setpassword"	method="post">

	<table>
		<tr>
			<td><form:label for="password" path="password" cssErrorClass="error">Password:</form:label></td>
			<td><form:input path="password" /></td>
			<td><form:errors path="password" /></td>
		</tr>
		<tr>
			<td><form:label for="confirmPassword" path="confirmPassword" cssErrorClass="error">Confirm Password:</form:label></td>
			<td><form:input path="confirmPassword" /></td>
			<td><form:errors path="confirmPassword" /></td>
		</tr>
		<tr>
			<td colspan="3" align="center"><input type="submit"
				value="change" /></td>
		</tr>
	</table>
	</form:form>


</body>
</html>
