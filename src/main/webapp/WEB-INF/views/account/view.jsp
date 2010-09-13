<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<META  http-equiv="Content-Type"  content="text/html;charset=UTF-8">
<title>View Account</title>
</head>
<body>

<form:form modelAttribute="account" action="${account.id}" method="post">
	<fieldset><legend>Account Fields</legend>
	<p><form:label for="username" path="name">Username</form:label><br />
	<form:input path="username" readonly="true" /></p>
	<p><form:label for="email" path="email">Email Address</form:label><br />
	<form:input path="email" readonly="true" /></p>
	</fieldset>
</form:form>
</body>
</html>


