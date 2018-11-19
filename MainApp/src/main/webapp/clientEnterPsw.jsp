<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Enter password</title>
</head>
<body>
<c:set value="${pageContext.request.contextPath}" var="contextPath"/>
    <form:form modelAttribute="contract" action="${contextPath}/${contract.id}/confirmPsw" method="post">
        Enter your password: <form:password path="password"/>
        <input type="submit" value="Log in"/>
    </form:form>

    <form action="${contextPath}/clientLogInForm" method="get">
        <input type="submit" value="Return"/>
    </form>
</body>
</html>