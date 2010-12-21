<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<html>
        <%@ include file="/WEB-INF/jsp/header.jsp"%>
${editlink}

${message}
<div class="review">
    <h1>Review for ${review.snippet.title} by ${review.snippet.author.username}</h1>
    <div class="user">Author: ${review.author.firstname} ${review.author.lastname} (${review.author.username})</div>
    <div class="lastModified">Last Modfied: <fmt:formatDate pattern="yyyy/MM/dd hh:mm" value="${review.author.lastModifiedDate}"/></div>
    <div class="snippetContent">
        ${snippet.content}
    </div>
    <div class="reviewContent">
        ${review.content}
    </div>
</div>

	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	</body>
</html>


