<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tariff</title>
    <jsp:include page="header.jsp"/>
</head>
<body>
<c:set value="${pageContext.request.contextPath}" var="contextPath"/>
<div style="margin-left: 50px; margin-top: 20px; margin-right: 50px">
    <form action="${contextPath}/employee/showAllContracts" method="get">
        <button type="submit" class="btn btn-primary btn-lg">Return</button>
    </form>

    <div style="float: left; margin-right: 100px" class="custom-select-lg">
        <form:form modelAttribute="contract" method="post" action="changeTariff">
        <h1 style="margin-top: 100px">Tariff</h1>
        <form:select path="tariff.id">
            <c:forEach items="${tariffList}" var="tariff">
                <form:option value="${tariff.id}">${tariff.name} (${tariff.description})</form:option>
            </c:forEach>
        </form:select>
    </div>
    <button type="submit" class="btn btn-primary btn-lg" style="margin-top: 165px">Apply</button
    </form:form>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
