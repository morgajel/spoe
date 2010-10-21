<%--
	| Looks good
--%>
<html>
    <head>
        <title>Oops!</title>
        <link rel="stylesheet" type="text/css" href="/css/layout.css" />
        <meta http-equiv="refresh" content="5;url=${pageContext.request.contextPath}/">
    </head>
    <body>
        <div id='header' >
            <%@ include file="/WEB-INF/jsp/loginbox.jsp"%>
            <img src="/images/spoe-icon.png" id="logo"/>
            <span style="font-size:30px;">Second Pair of Eyes</span>
        </div>
        <div id="content">
            <h3>Oops! An exception!</h3>
            <pre>
                ${exception}
            </pre>
                
            <p>
                Sorry, this shouldn't have happend. Don't worry, the people responsible will be smacked.
            </p>        
        </div>
    </body>
</html>
