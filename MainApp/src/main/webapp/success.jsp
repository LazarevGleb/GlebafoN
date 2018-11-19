<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Success</title>
</head>
<body>
<c:set value="${pageContext.request.contextPath}" var="contextPath"/>
<h1> Your operation was completed successfully!</h1>
<form action="${contextPath}/returnToStartPage" method="get">
    <input type="submit" value="Return"/>
</form>
</body>
</html>
