<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>
<html>
        <%@ include file="/WEB-INF/jsp/header.jsp"%>


Welcome to your account page, ${account.username}.
<div style="position:relative;top:5px;right:5px;float:right;"><a href="/account/edit">edit account</a></div>
<hr/>
<table border='1'>
<tr>
     		<th>Snippet ID</th>
          	<th> Title</th>
          	<th>Last Modified</th>
        </tr>
	<c:forEach var="snippet" items="${account.snippets}"> 
   		<tr>
     		<td>${snippet.snippetId}</td>
          	<td>${snippet.title}</td>
          	<td>${snippet.lastModifiedDate}</td>
        </tr>
	</c:forEach>
</table>
<br/>

${message}
	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	</body>
</html>


