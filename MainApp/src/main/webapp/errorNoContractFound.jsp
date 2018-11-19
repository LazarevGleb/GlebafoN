<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
    <jsp:include page="header.jsp"/>
</head>
<style type="text/css">
    .error {
        background: #17a2b8;
        color: white;
        border-radius: 1em;
        padding: 1em;
        position: absolute;
        top: 50%;
        left: 50%;
        margin-right: -50%;
        transform: translate(-50%, -50%)
    }
</style>
<body>
<c:set value="${pageContext.request.contextPath}" var="contextPath"/>
<div class="container text-center error">
    <p style="font-size: 4em">No contract with such number found :(</p>

    <form action="${contextPath}/login" method="get">
        <input type="submit" value="Return" class="btn btn-lg" style="font-size: x-large"/>
    </form>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
