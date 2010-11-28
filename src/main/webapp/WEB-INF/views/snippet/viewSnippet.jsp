<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>
<html>
        <%@ include file="/WEB-INF/jsp/header.jsp"%>
${editlink}

${message}
<div class="snippet">
    <h1>${snippet.title}</h1>
    <div class="user">Author: ${snippet.author.firstname} ${snippet.author.lastname} (${snippet.author.username})</div>
    <div class="lastModified">Last Modfied: <fmt:formatDate pattern="yyyy/MM/dd hh:mm" value="${snippet.author.lastModifiedDate}" /></div>
    <div class="snippetContent">
        ${snippet.content}
    </div>
</div>

	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	</body>
</html>


