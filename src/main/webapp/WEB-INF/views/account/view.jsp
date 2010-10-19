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
<%@ include file="/WEB-INF/jsp/snippetList.jsp"%>
<br/>

${message}
	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	</body>
</html>


