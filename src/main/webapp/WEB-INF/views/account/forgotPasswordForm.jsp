<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>
<html>
        <%@ include file="/WEB-INF/jsp/header.jsp"%>

<h2> Password Reset Request Form</h2>
${message}

<form:form modelAttribute="forgotPasswordForm" action="/account/forgotPassword.submit" method="post">

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
            <td colspan="3" align="center"><input type="submit"
                value="submit" /></td>
        </tr>
    </table>
    <i>Note:</i> A reset email will be sent to you.
    </form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>
    </body>
</html>
