<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="/css/layout.css" />
<script type="text/javascript" src="/js/jquery-1.4.2.min.js"> </script>
<script type="text/javascript" src="/js/jquery.dropmenu.js"></script>
<script language="javascript" type="text/javascript">
    $(document).ready(function() {
        $('#topmenu').dropmenu(
            {
                effect          : "slide",       //  "slide", "fade", or "none"
                speed           : "fast",     //  "normal", "fast", "slow", 100, 1000, etc
                timeout         : 250,
                nbsp            : true,
                maxWidth        : 0
            }
        );
    });
</script>

<link type="text/css" rel="stylesheet" href="/css/dropmenu.css" />

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


<div id="menubox">
                <form method="get" action="/search"  id='searchform' >
                    <input type="text" value="quick search" id="search_box" onclick='searchClear(this);'/>
                    <input type="submit" value="Search" id="search_button"  />
                </form>
                
    <ul id="topmenu" class="dropmenu">
        <li ><a href="/" class="first">Home</a></li>
        <li><a href="/review/">Reviews</a>
            <ul>
                <sec:authorize access='isAuthenticated()'>
                <li><a href="/review/my">My Reviews</a></li>
                </sec:authorize>
                <li><a href="/review/create">Write a Review</a></li>
                <li><a href="/review/find">Find A Review</a></li>
            </ul>
        </li>
        <li><a href="/snippet/">Snippets</a>
            <ul>
                <sec:authorize access='isAuthenticated()'>
                <li><a href="/snippet/my">My Snippets</a></li>
                </sec:authorize>
                <li><a href="/snippet/create">Create A Snippet</a></li>
                <li><a href="/snippet/find">Find A Snippet</a></li>
            </ul>
        </li>
        <li><a href="/search/advanced">Advanced Search</a></li>
        <sec:authorize access='isAuthenticated()'>
            <li><a href="/account">My Account</a>
                <ul>
                    <li><a href="/snippet/my">My Snippets</a></li>
                    <li><a href="/review/my">My Reviews</a></li>
                    <li><a href="/account/edit">Edit Account</a></li>
                </ul>
            </li>
        </sec:authorize>
    </ul>
</div>
 <div id="content">




