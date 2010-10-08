<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="/css/layout.css" />
<script type="text/javascript" src="/js/jquery-1.4.2.min.js"></script> 
<title>${title}</title>
</head>
<body style="margin-left:auto;margin-right:auto;width:900px;">
<script type="text/javascript">
function searchClear(searchbox){
	if ($(searchbox).val() == "quick search"){
		$(searchbox).val('');
		$(searchbox).css('color','black');
	}
}

</script>
<div id='header' style="height:100px;vertical-align:middle;">
<img src="/images/spoe-icon.png" style="vertical-align:middle;padding-left:10px;"/>
<span style="font-size:30px;">Second Pair of Eyes</span>

<%@ include file="/WEB-INF/jsp/loginbox.jsp"%>

</div>
<hr width="100%"/>
    
        
    

<ul id="top-navigation" style="float:left;" >

    <li><a href="/"><span>Home</span></a></li>
    <li><a href="/review/"><span>Submit A Review</span></a></li>
    <li><a href="/snippet/"><span>Submit A Snippet</span></a></li>
    <li><a href="/search/advanced"><span>Advanced Search</span></a></li>
    <sec:authorize access='isAuthenticated()'>
    <li><a href="/account"><span>My Account</span></a></li>
    </sec:authorize>
    <li>
        <form method="get" action="/search">
        <input type="submit" value="Search" class="search_button"  style="float:right;height:22px;"/>
            <input type="text" value="quick search" name="search" id="search_box" class='search_box' onclick='searchClear(this);'/>
        </form>
    </li>
    
    
</ul>

<br clear="all"/>
    

<div id="content-window">




