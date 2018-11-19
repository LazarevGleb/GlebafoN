<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Add new client</title>
</head>
<jsp:include page="header.jsp"/>
<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/smoothness/jquery-ui.css"
      rel="stylesheet" type="text/css"/>
<script>
    $( function() {
        $( "#datepicker" ).datepicker();
    } );
</script>
<body>
<div style="margin-left: 50px; margin-top: 20px">
    <c:set value="${pageContext.request.contextPath}" var="contextPath"/>
    <h5 class="display-3">Enter client info</h5>
    <form:form modelAttribute="client" method="post" action="${contextPath}/employee/addClient">
        <div style="float: left; margin-right: 100px">
            <h5>Name</h5>
            <spring:bind path="name">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="name" cssClass="form-control-lg" placeholder="Name"
                                autofocus="true"/>
                    <form:errors path="name"/>
                </div>
            </spring:bind>

            <h5>Surname</h5>
            <spring:bind path="surname">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="surname" cssClass="form-control-lg" placeholder="Surname"
                                required="true"/>
                    <form:errors path="surname"/>
                </div>
            </spring:bind>

            <h5>Birthday</h5>
            <spring:bind path="birthday">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="date" path="birthday" cssClass="form-control-lg" required="true"/>
                                <%--pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}"--%>
                    <form:errors path="birthday"/>
                </div>
            </spring:bind>

            <h5>Address</h5>
            <spring:bind path="address">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="address" cssClass="form-control-lg"
                                required="true"/>
                    <form:errors path="address"/>
                </div>
            </spring:bind>

            <h5>Email</h5>
            <spring:bind path="email">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="email" path="email" cssClass="form-control-lg" placeholder="xxx@xxx.xx"
                                pattern="^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$"
                                required="true"/>
                    <form:errors path="email"/>
                </div>
            </spring:bind>
        </div>
        <div style="float: left; margin-right: 100px">
            <h5>Series</h5>
            <spring:bind path="passportSeries">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="passportSeries" cssClass="form-control-lg" placeholder="XXXX"
                                pattern="[0-9]{4}" required="true"/>
                    <form:errors path="passportSeries"/>
                </div>
            </spring:bind>

            <h5>Number</h5>
            <spring:bind path="passportNumber">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="passportNumber" cssClass="form-control-lg"
                                placeholder="XXXXXX" pattern="[0-9]{6}" required="true"/>
                    <form:errors path="passportNumber"/>
                </div>
            </spring:bind>

            <h5>Issued by</h5>
            <spring:bind path="passportIssuedBy">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="passportIssuedBy" cssClass="form-control-lg"
                                placeholder="Issued by..." required="true"/>
                    <form:errors path="passportIssuedBy"/>
                </div>
            </spring:bind>

            <h5>Date of issue</h5>
            <spring:bind path="passportIssueDate">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="date" path="passportIssueDate" cssClass="form-control-lg"
                                pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}"
                                placeholder="01.01.0001" required="true"/>
                    <form:errors path="passportIssueDate"/>
                </div>
            </spring:bind>

            <h5>Division code</h5>
            <spring:bind path="passportDivisionCode">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="passportDivisionCode" cssClass="form-control-lg"
                                pattern="[0-9]{3}-[0-9]{3}"
                                placeholder="XXX-XXX" required="true"/>
                    <form:errors path="passportDivisionCode"/>
                </div>
            </spring:bind>
        </div>
        <div style="float: left">
            <h5>Contract number</h5>
            <spring:bind path="number">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:select path="number">
                        <c:forEach items="${numberList}" var="num">
                            <form:option value="${num}">${num}</form:option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="number"/>
                </div>
            </spring:bind>

            <h5>Balance</h5>
            <spring:bind path="balance">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="balance" cssClass="form-control-lg"
                                placeholder="0.00" required="true"/>
                    <form:errors path="passportDivisionCode"/>
                </div>
            </spring:bind>

            <h5>Tariff</h5>
            <div class="form-control-lg" style="margin-bottom: 50px">
                <form:select path="tariffId">
                    <c:forEach items="${tariffList}" var="tariff">
                        <form:option value="${tariff.id}">${tariff.name}</form:option>
                    </c:forEach>
                </form:select>
            </div>

            <h5>Options</h5>
            <div class="form-control-lg" style="margin-bottom: 200px">
                <form:select path="additionIds" multiple="true">
                    <c:forEach items="${additionList}" var="add">
                        <form:option value="${add.id}">${add.name}</form:option>
                    </c:forEach>
                </form:select>
            </div>

            <div>
                <button type="submit" class="btn btn-primary btn-lg">Submit</button>
            </div>
        </div>
    </form:form>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>