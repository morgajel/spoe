<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<html>
        <%@ include file="/WEB-INF/jsp/header.jsp"%>
<hr/>

${message}
<br/>


<div class="snippetList">
<h2>Search Results</h2>
    <display:table name="${results}" id="snippet" pagesize="10" keepStatus="true" requestURI="/search" defaultsort="1" sort="list"> 
        <display:column property="snippetId" title="ID" class='id'  format="{0,number,0}" sortable="true" />
        <display:column title="Title"  sortable="true">
            <a href="/snippet/id/${snippet.snippetId}">${snippet.title}</a>
        </display:column>
        <display:column property="lastModifiedDate" title="Last Modified" format="{0,date,yyyy/MM/dd hh:mm:ss a}"   sortable="true"  />
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


