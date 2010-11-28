<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<div class="snippetList">
<h2>${param.snippetTitle}</h2>
    <display:table name="snippets" id="snippet" pagesize="${param.pageSize}" keepStatus="true" requestURI="/account" defaultsort="1" sort="list" defaultorder="descending"> 
        <display:column property="snippetId" title="ID" class='id'  format="{0,number,0}" sortable="true" />
        <display:column title="Title"  sortable="true">
            <a href="/snippet/id/${snippet.snippetId}">${snippet.title}</a>
        </display:column>
        <display:column property="lastModifiedDate" title="Last Modified" format="{0,date,yyyy/MM/dd hh:mm a}"   sortable="true"  />
        <sec:authorize access='isAuthenticated()'>
            <display:column title="Edit" >
                <a href="/snippet/edit/${snippet.snippetId}">[edit]</a>
            </display:column>
        </sec:authorize>
    </display:table>
    
</div>