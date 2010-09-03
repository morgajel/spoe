<%--
	| Looks good
--%>
<html>
    <head>
        <title>Oops! An exception!</title>
        <meta http-equiv="refresh" content="5;url=${pageContext.request.contextPath}/">
    </head>
    <body>
        <h3>Oops! An exception!</h3>
        <pre>
            ${exception}
        </pre>
                
        <p>
            This page is displayed when an exception is caught by the Servlet container.<br/>
            You will be redirected in 5 seconds to the home page.<br/>            
            It is configured in web.xml<br/>
            I should log this.
        </p>        
    </body>
</html>
