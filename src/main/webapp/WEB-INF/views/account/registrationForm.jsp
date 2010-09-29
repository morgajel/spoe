<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<meta  http-equiv="Content-Type"  content="text/html;charset=UTF-8">
<title>Registration Form</title>
</head>
<body>
${message}

<form:form modelAttribute="registrationForm" action="/account/register.submit" method="post">

	<table>

		<tr>
			<td><form:label for="username" path="username"
				cssErrorClass="error">Username:</form:label></td>
			<td><form:input path="username" /></td>
			<td><form:errors path="username" /></td>
		</tr>

		<tr>
			<td><form:label for="email" path="email" cssErrorClass="error">Email Address:</form:label></td>
			<td><form:input path="email" /></td>
			<td><form:errors path="email" /></td>
		</tr>
		<tr>
			<td><form:label for="confirmEmail" path="confirmEmail" cssErrorClass="error">Confirm Email Address:</form:label></td>
			<td><form:input path="confirmEmail" /></td>
			<td><form:errors path="confirmEmail" /></td>
		</tr>

		<tr>
			<td><form:label for="firstname" path="firstname"
				cssErrorClass="error">First Name:</form:label></td>
			<td><form:input path="firstname" /></td>
			<td><form:errors path="firstname" /></td>
		</tr>

		<tr>
			<td><form:label for="lastname" path="lastname"
				cssErrorClass="error">Last Name:</form:label></td>
			<td><form:input path="lastname" /></td>
			<td><form:errors path="lastname" /></td>
		</tr>


		<tr>
			<td colspan="3" align="center"><input type="submit"
				value="register" /></td>
		</tr>
	</table>
	<i>Note:</i> An activation email will be sent to you.
	</form:form>


</body>
</html>
