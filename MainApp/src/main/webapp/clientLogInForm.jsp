<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Client log in</title>
</head>
<body>
<c:set value="${pageContext.request.contextPath}" var="contextPath"/>
<form:form modelAttribute="contract" method="post" action="${contextPath}/clientLogIn">
    <label for="phone">Phone number:</label>
    <form:input path="number" type="tel" id="phone" name="phone" placeholder="+7-XXX-XXX-XXXX"
           pattern="\+7-[0-9]{3}-[0-9]{3}-[0-9]{4}" required="true"/><br/>

    <input type="submit" value="Enter"/>
</form:form>

<form action="${contextPath}/returnToStartPage" method="get">
    <input type="submit" value="Return"/>
</form>
</body>
</html>
