<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
        <%@ include file="/WEB-INF/jsp/header.jsp"%>


${account.username}, these are your current snippets.
<hr/>

${message}
<br/>
    <jsp:include page="/WEB-INF/jsp/snippetList.jsp">
        <jsp:param name="snippetTitle" value="My Snippets"/>
        <jsp:param name="pageSize" value="10"/>
        <jsp:param name="snippets" value="${account.snippets}"/>
    </jsp:include>

    <%@ include file="/WEB-INF/jsp/footer.jsp"%>
    </body>
</html>
