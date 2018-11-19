<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Balance</title>
    <jsp:include page="header.jsp"/>
</head>
<body>
<c:set value="${pageContext.request.contextPath}" var="contextPath"/>
<div style="margin-left: 50px; margin-top: 20px; margin-right: 50px">
    <form action="${contextPath}/employee/showAllContracts" method="get">
        <button type="submit" class="btn btn-primary btn-lg">Return</button>
    </form>
    <div class="display-4">
        Current balance: ${contract.balance} &#8381
    </div>

    <div style="float: left; margin-right: 100px">
        <form:form modelAttribute="contract" action="balance" method="post">
        <h1 style="margin-top: 100px">Top up the balance</h1>
        <spring:bind path="balance">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="balance" cssClass="form-control-lg" placeholder="0.00"
                            onkeypress="return event.charCode >= 48 || event.key == 'Delete'"
                            required="true" autofocus="true"/>
                <form:errors path="balance"/>
            </div>
        </spring:bind>
    </div>
    <button type="submit" class="btn btn-primary btn-lg" style="margin-top: 165px">Apply</button>
    </form:form>
</div>

<jsp:include page="footer.jsp"/>
</body>
</html>

