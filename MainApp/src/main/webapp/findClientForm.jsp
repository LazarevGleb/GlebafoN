<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="header.jsp"/>
</head>
<body>
<c:set value="${pageContext.request.contextPath}" var="contextPath"/>
<div style="float: left; margin-left: 50px; margin-top: 20px; margin-right: 200px; width: 500px; font-size: x-large"
     class="custom-select-lg">
    <form action="${contextPath}/employee/showAllClients" method="get">
        <button type="submit" class="btn btn-primary btn-lg">Return</button>
    </form>
    <h1 style="margin-top: 100px">Find client by number</h1>
    <form:form modelAttribute="contract" action="showSearchResult" method="post">
        <form:input type="text" path="number" cssClass="form-control-lg" placeholder="+7-XXX-XXX-XXXX"
                    pattern="\+7-[0-9]{3}-[0-9]{3}-[0-9]{4}" required="true" autofocus="true"/>
        <form:errors path="number"/>
        <button type="submit" class="btn btn-primary btn-lg" style="margin-left: 100px">Find</button>
    </form:form>
</div>

<div style="float: left; margin-top: 20px; margin-right: 50px; width: 1000px;
font-size: x-large">
    <c:if test="${client != null}">
        <form:form modelAttribute="client">
            <h1 class="text-center">Result of search</h1><br/>
            <p><b>Name: </b>  ${client.name}</p>
            <p><b>Surname: </b>  ${client.surname}</p>
            <p><b>Birthday: </b>  ${client.birthday}</p>
            <p><b>Address: </b> ${client.address}</p>
            <p><b>Email: </b>  ${client.email}</p>
            <p><b>Passport </b>
            <p><b>Series: </b>  ${client.passportSeries}</p>
            <p><b>Number: </b>  ${client.passportNumber}</p>
            <p><b>Issued by: </b>  ${client.passportIssuedBy}</p>
            <p><b>Issue date: </b>  ${client.passportIssueDate}</p>
            <p><b>Division code: </b>  ${client.passportDivisionCode}</p>
        </form:form>
    </c:if>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
