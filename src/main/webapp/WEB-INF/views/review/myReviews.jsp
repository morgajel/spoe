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


${account.username}, these are your current Reviews.
<hr/>

${message}
<br/>
    <jsp:include page="/WEB-INF/jsp/reviewList.jsp">
        <jsp:param name="reviewTitle" value="My Reviews"/>
        <jsp:param name="pageSize" value="10"/>
        <jsp:param name="reviews" value="${account.reviews}"/>
    </jsp:include>

    <%@ include file="/WEB-INF/jsp/footer.jsp"%>
    </body>
</html>
