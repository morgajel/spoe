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


Welcome to your account page, ${account.username}.
<div style="position:relative;top:5px;right:5px;float:right;"><a href="/account/edit">edit account</a></div>
<hr/>

${message}
<br/>
<div class="snippetList">
<h2>Recent Snippets</h2>
    <display:table name="${account.snippets}" id="snippet" pagesize="5" keepStatus="true" requestURI="/account"> 
        <display:column property="snippetId" title="ID" class='id'  format="{0,number,0}" sortable="true" />
        <display:column title="Title"  sortable="true">
            <a href="/snippet/id/${snippet.snippetId}">${snippet.title}</a>
        </display:column>
        <display:column property="lastModifiedDate" title="Last Modified" format="{0,date,yyyy/dd/mm hh:mm:ss a}"   sortable="true"/>
        <sec:authorize access='isAuthenticated()'>
            <display:column title="Editable" >
                <a href="/snippet/edit/${snippet.snippetId}">[edit]</a>
            </display:column>
        </sec:authorize>
    </display:table>
</div>

	<%@ include file="/WEB-INF/jsp/footer.jsp"%>
	</body>
</html>


