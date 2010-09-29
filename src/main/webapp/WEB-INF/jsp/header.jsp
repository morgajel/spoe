<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${title}</title>
</head>
<body>

<div style="top: 5px;">
<a href="/">Home</a> | 
<a href="/review">Reviews</a> | 
<a href="/snippet">Snippets</a>
<sec:authorize access='isAuthenticated()'>
 | <a href="/review/add">Add a Review</a> | 
 <a href="/snippet/add">Add a Snippet</a>
</sec:authorize>
</div>
<%@ include file="/WEB-INF/jsp/loginbox.jsp"%>


