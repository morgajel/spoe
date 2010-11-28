<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
        <%@ include file="/WEB-INF/jsp/header.jsp"%>


Welcome to your account page, ${account.username}.
<div style="position:relative;top:5px;right:5px;float:right;"><a href="/account/edit">edit account</a></div>
<hr/>

${message}
<br/>
    <jsp:include page="/WEB-INF/jsp/snippetList.jsp" >
        <jsp:param name="snippetTitle" value="Recent Snippets"/>
        <jsp:param name="pageSize" value="5"/>
        <jsp:param name="snippets" value="${snippets}"/>
    </jsp:include>

     
	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	</body>
</html>


