<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Entrance</title>
</head>
<body>
<c:set value="${pageContext.request.contextPath}" var="contextPath"/>
<div class="">
    <form:form modelAttribute="admin" method="post" action="${contextPath}/employee/logIn">
        Login: <form:input type="text" path="login" placeholder="Your login"/><br/>


        Password: <form:password path="password" pattern="[a-zA-z0-9]{5,}"/><br/>

        <input type="submit" value="Log in"/>
    </form:form>

    <button type="button" name="back" onclick="history.back()">Back</button>
</div>
</body>
</html>
