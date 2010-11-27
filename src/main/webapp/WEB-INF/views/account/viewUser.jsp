<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
        <%@ include file="/WEB-INF/jsp/header.jsp"%>
${message} 

Info about ${account.username}.
<table>
<tr><th>Name:</th><td> ${account.firstname} ${account.lastname} </td></tr>
<tr><th>Email:</th><td> ${account.email} </td></tr>
<tr><th>Member Since:</th><td> ${account.creationDate} </td></tr>
<tr><th>Writing Experience:</th><td> ${account.writingExperience} </td></tr>
<tr><th>Reviewing Experience:</th><td> ${account.reviewingExperience} </td></tr>
</table>

    <jsp:include page="/WEB-INF/jsp/snippetList.jsp">
        <jsp:param name="snippetTitle" value="Snippets from ${account.username}"/>
        <jsp:param name="pageSize" value="10"/>
    </jsp:include>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>
    </body>
</html>
