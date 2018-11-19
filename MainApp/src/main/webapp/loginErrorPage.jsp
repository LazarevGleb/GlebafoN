<%--
  Created by IntelliJ IDEA.
  User: gleb
  Date: 10.09.18
  Time: 23:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
<c:set value="${pageContext.request.contextPath}" var="contextPath"/>
    <h2> No manager with such login and password found :(</h2>
    <h4> Please ask admin to register you!</h4>
    <div class="">
        <form action="${contextPath}/login" method="get">
            <input type="submit" value="Return">
        </form>
    </div>
</body>
</html>
