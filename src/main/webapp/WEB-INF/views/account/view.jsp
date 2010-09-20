<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<META  http-equiv="Content-Type"  content="text/html;charset=UTF-8">
<title>View Account</title>
</head>
<body>
Welcome to your account page, ${account.username}.
<hr/>
${message} 
</body>
</html>


