<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="/css/layout.css" />
<title>${title}</title>
</head>
<body style="margin-left:auto;margin-right:auto;width:900px;">
<div id='header' style="height:100px;background-color:#dddddd;vertical-align:middle;">
<img src="/images/spoe-icon.png" style="vertical-align:middle;padding-left:20px;"/>
<span style="font-size:30px;">Second Pair of Eyes</span>

<%@ include file="/WEB-INF/jsp/loginbox.jsp"%>

</div>
<hr width="100%"/>

<ul id="navigation" >
    <li><a href="/"><span>Home</span></a></li>
    <li><a href="/review/"><span>Submit A Review</span></a></li>
    <li><a href="/snippet/"><span>Submit A Snippet</span></a></li>
    <li><a href="/search/advanced"><span>Advanced Search</span></a></li>
</ul>
<br clear="all"/>
<div style="background-color:#00dddd;border:1px solid black;">




