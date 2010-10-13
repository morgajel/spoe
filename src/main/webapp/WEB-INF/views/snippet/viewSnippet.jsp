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
<h1>${snippet.title}(${snippet.snippetId})</h1>

<hr/>
${snippet.content}
<hr/>
${message}
	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	</body>
</html>


