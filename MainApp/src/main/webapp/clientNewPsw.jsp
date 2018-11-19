<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: gleb
  Date: 17.09.18
  Time: 12:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New password</title>
</head>
<body>
<c:set value="${pageContext.request.contextPath}" var="contextPath"/>
<form:form modelAttribute="contract" method="post" action="${contextPath}/${contract.id}/pswAdd">
    Enter and remember new password:<form:input path="password"/>
    <input type="submit" value="Add new password"/>
</form:form>

<form action="${contextPath}/clientLogInForm" method="get">
    <input type="submit" value="Return"/>
</form>
</body>
</html>
