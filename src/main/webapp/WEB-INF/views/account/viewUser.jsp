<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>

	<%@ include file="/WEB-INF/jsp/header.jsp"%>
Info about ${account.username}.
<table>
<tr><td> ${account.firstname} ${account.lastname} </td></tr>
<tr><td> ${account.email} </td></tr>
<tr><td> ${account.creationDate} </td></tr>
</table>



${message} 
	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
</body>
</html>


