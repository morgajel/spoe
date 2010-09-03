<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" 
%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" 
%><%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" 
%><%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" 
%><%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--
	Need to go over the above tags a little closer.
--%>
<head>
    <title>An exception was caught!</title>
    <meta http-equiv="refresh" content="5;url=${pageContext.request.contextPath}/">
</head>
<body>
    <p>An exception was caught!</p>
    <div id="errors">
        <pre>${exception}</pre>
    </div>
    <p>
        This page is displayed when an exception is caught by Spring MVC.<br/>
        You will be redirected in 5 seconds to the home page.<br/>            
        It is configured in your Spring MVC configuration file (look for the exceptionResolver bean).
        (This message is fine for now.)
    </p>
</body>
