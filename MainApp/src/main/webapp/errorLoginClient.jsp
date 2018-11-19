<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
<c:set value="${pageContext.request.contextPath}" var="contextPath"/>
<h2>No client with a such data found :(</h2>

<form action="${contextPath}/clientLogInForm" method="get">
    <input type="submit" value="Return"/>
</form>
</body>
</html>
