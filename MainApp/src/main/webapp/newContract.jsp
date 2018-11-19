<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New number</title>
    <jsp:include page="header.jsp"/>
</head>
<body>
<c:set value="${pageContext.request.contextPath}" var="contextPath"/>
<div style="margin-left: 50px; margin-top: 20px; margin-right: 50px">
    <form action="${contextPath}/employee/showAllClients" method="get">
        <button type="submit" class="btn btn-primary btn-lg">Return</button>
    </form>
    <form:form modelAttribute="contract" method="post"
               action="${contextPath}/employee/${client.id}/addNumber">
        <div style="float: left; margin-right: 100px">
            <h1 style="margin-top: 100px">Number</h1>
            <form:select path="number">
                <c:forEach items="${numberList}" var="num">
                    <form:option value="${num}">${num}</form:option>
                </c:forEach>
            </form:select>
        </div>

        <div style="float: left; margin-right: 100px" class="custom-select-lg">
            <h1 style="margin-top: 100px">Tariff</h1>
            <form:select path="tariffId">
                <c:forEach items="${tariffList}" var="tariff">
                    <form:option value="${tariff.id}">${tariff.name} (${tariff.description})</form:option>
                </c:forEach>
            </form:select>

            <h1 style="margin-top: 20px">Balance</h1>
            <spring:bind path="balance">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="balance" cssClass="form-control-lg"
                                placeholder="0.00" required="true" autofocus="true"/>
                    <form:errors path="balance"/>
                </div>
            </spring:bind>
        </div>

        <div style="float: left; margin-right: 100px" class="custom-select-lg">
            <h1 style="margin-top: 100px">Options</h1>
            <form:select path="additionIds" multiple="true">
                <c:forEach items="${additionList}" var="add">
                    <form:option value="${add.id}">${add.name}</form:option>
                </c:forEach>
            </form:select>
        </div>

        <button type="submit" class="btn btn-primary btn-lg" style="margin-top: 300px">Submit</button>
    </form:form>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
