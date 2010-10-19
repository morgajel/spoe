<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table class="tablesorter tableExpander">
    <thead> 
        <tr>
            <th class='id'><div>ID</div></th>
            <th><div>Title</div></th>
            <th><div>Last Modified</div></th>
            <sec:authorize access='isAuthenticated()'>
                <th>Edit</th>
            </sec:authorize>
        </tr>
    </thead>
    <tbody> 
    <c:forEach var="snippet" items="${account.snippets}"> 
        <tr>
            <td class='id'>${snippet.snippetId}</td>
            <td><a href="/snippet/id/${snippet.snippetId}">    <c:out value="${fn:substring(snippet.title,0,30)}..."/></a></td>
            <td><fmt:formatDate pattern="yyyy/MM/dd hh:mm:ss a" value="${snippet.lastModifiedDate}" /></td>
            <sec:authorize access='isAuthenticated()'>
                <td><a href="/snippet/edit/${snippet.snippetId}">[edit]</a></td>
            </sec:authorize>
        </tr>
    </c:forEach>
    </tbody>
</table>