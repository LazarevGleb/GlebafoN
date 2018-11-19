<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Number</title>
    <jsp:include page="header.jsp"/>
</head>
<body>
<c:set value="${pageContext.request.contextPath}" var="contextPath"/>
<div style="margin-left: 50px; margin-top: 20px; margin-right: 50px">
    <form action="${contextPath}/employee/showAllContracts" method="get">
        <button type="submit" class="btn btn-primary btn-lg">Return</button>
    </form>
    <div style="float: left; margin-right: 100px">
        <form:form modelAttribute="contract" action="changeNumber" method="post">
        <h1 style="margin-top: 100px">Number</h1>
        <spring:bind path="number">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:select path="number">
                    <form:option value="${contract.number}">${contract.number}</form:option>
                    <c:forEach items="${numberList}" var="num">
                        <form:option value="${num}">${num}</form:option>
                    </c:forEach>
                </form:select>

            </div>
        </spring:bind>
    </div>
    <button type="submit" class="btn btn-primary btn-lg" style="margin-top: 165px">Apply</button>
    </form:form>
</div>

<jsp:include page="footer.jsp"/>
</body>
</html>
