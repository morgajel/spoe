<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="/css/layout.css" />
<script type="text/javascript" src="/js/jquery-1.4.2.min.js"/>
<title>${title}</title>
</head>
<body >
<script type="text/javascript">
function searchClear(searchbox){
	if ($(searchbox).val() == "quick search"){
		$(searchbox).val('');
		$(searchbox).css('color','black');
	}
}
</script>
 
<div id='header' >
<%@ include file="/WEB-INF/jsp/loginbox.jsp"%>
    <img src="/images/spoe-icon.png" id="logo"/>
    <span style="font-size:30px;">Second Pair of Eyes</span>
    

</div>

 <div style="text-align:right;width:900px;">
    <form method="get" action="/search" style="float:right;display:inline;margin:0px;padding:0px;" >
            <input type="text" value="quick search" id="search_box" onclick='searchClear(this);'/>
            <input type="submit" value="Search" id="search_button"  />
    </form>
 
<ul id='topmenu' > 
    <li><a href="/"><span>Home</span></a></li>
    <li><a href="/review/"><span>Submit A Review</span></a></li>
    <li><a href="/snippet/"><span>Submit A Snippet</span></a></li>
    <li><a href="/search/advanced"><span>Advanced Search</span></a></li>
    <sec:authorize access='isAuthenticated()'>
    <li><a href="/account"><span>My Account</span></a></li>
    </sec:authorize>
</ul> 
</div>
 <div id="content">




