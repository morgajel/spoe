<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<html>
        <%@ include file="/WEB-INF/jsp/header.jsp"%>

    ${message}

    <p>SPoE exists to give authors a place to post snippets and 
    samples of their material and get feedback from other users.</p>



<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<div class="snippetList">
    <h2>Recent Snippets</h2>
    <display:table name="recentSnippets" id="snippet" pagesize="5" keepStatus="true" requestURI="/" defaultsort="1" sort="list" defaultorder="descending"> 
        <display:column title="User">
            <a href="/account/user/${snippet.author.username}">${snippet.author.username}</a>
        </display:column>
        <display:column title="Title">
            <a href="/snippet/id/${snippet.snippetId}">${snippet.title}</a>
        </display:column>
        <display:column property="lastModifiedDate" title="Last Modified" format="{0,date,yyyy/MM/dd hh:mm a}" />
    </display:table>
</div>
<div class="snippetList">
    <h2>Recent Reviews</h2>
    <display:table name="recentReviews" id="review" pagesize="5" keepStatus="true" requestURI="/" defaultsort="1" sort="list" defaultorder="descending"> 
        <display:column title="Review" style="width:50px;">
            <a href="/review/id/${review.reviewId}"><img src="/images/review.png" style="border:0px;"/></a>
        </display:column>
        <display:column title="Reviewer">
            <a href="/account/user/${review.author.username}">${review.author.username}</a>
        </display:column>
        <display:column title="Snippet">
            <a href="/snippet/id/${review.snippet.snippetId}">${review.snippet.title}</a>
        </display:column>
        <display:column title="Author" >
            <a href="/account/user/${review.snippet.author.username}">${review.snippet.author.username}</a>
        </display:column>        
    </display:table>
</div>

    <%@ include file="/WEB-INF/jsp/footer.jsp"%>
    </body>
</html>


