<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta  http-equiv="Content-Type"  content="text/html;charset=UTF-8"/>
<title>Edit Account Form</title>
</head>
<body>
${message}

<form:form modelAttribute="eaForm" action="/account/edit.submit" method="post">
	<table>
		<tr>
			<td><form:label for="email" path="email" cssErrorClass="error">Change Email Address:</form:label></td>
			<td><form:input path="email" /></td>
			<td><form:errors path="email" /></td>
		</tr>
		<tr>
			<td><form:label for="confirmEmail" path="confirmEmail" cssErrorClass="error">Confirm Email Address:</form:label></td>
			<td><form:input path="confirmEmail" /></td>
			<td><form:errors path="confirmEmail" /></td>
		</tr>

		<tr>
			<td><form:label for="firstname" path="firstname" cssErrorClass="error">First Name:</form:label></td>
			<td><form:input path="firstname" /></td>
			<td><form:errors path="firstname" /></td>
		</tr>

		<tr>
			<td><form:label for="lastname" path="lastname" cssErrorClass="error">Last Name:</form:label></td>
			<td><form:input path="lastname" /></td>
			<td><form:errors path="lastname" /></td>
		</tr>
		<tr>
			<td><form:label for="password" path="password" cssErrorClass="error">Change Password:</form:label></td>
			<td><form:password path="password" /></td>
			<td><form:errors path="password" /></td>
		</tr>
		<tr>
		<td><form:label for="confirmPassword" path="confirmPassword" cssErrorClass="error">Confirm Password:</form:label></td>
			<td><form:password path="confirmPassword" /></td>
			<td><form:errors path="confirmPassword" /></td>
		</tr>
		<tr>
			<td colspan="3" align="center"><input type="submit"	value="register" /></td>
		</tr>
	</table>
	</form:form>

</body>
</html>