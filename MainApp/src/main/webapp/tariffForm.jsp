<%@ page session="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="header.jsp"/>
    <title>Tariff form</title>
</head>
<body>
<c:set value="${pageContext.request.contextPath}" var="contextPath"/>
<div style="margin-left: 50px">

    <c:choose>
        <c:when test="${tariff['new']}">
            <h1>Add tariff</h1>
        </c:when>
        <c:otherwise>
            <h1>Update tariff</h1>
        </c:otherwise>
    </c:choose>
    <br/>

    <spring:url value="/employee/tariffs" var="tariffActionUrl"/>

    <form:form class="form-horizontal" method="post" modelAttribute="tariff"
               action="${tariffActionUrl}">

        <form:hidden path="id"/>

        <spring:bind path="name">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label">Name</label>
                <div class="col-sm-10">
                    <form:input path="name" type="text" class="form-control " id="name"
                                placeholder="Name" required="true"/>
                    <form:errors path="name" class="control-label"/>
                </div>
            </div>
        </spring:bind>

        <spring:bind path="description">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label">Description</label>
                <div class="col-sm-10">
                    <form:input path="description" type="text" class="form-control" id="description"
                                placeholder="description" required="true"/>
                    <form:errors path="description" class="control-label"/>
                </div>
            </div>
        </spring:bind>

        <spring:bind path="sms">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label">SMS</label>
                <div class="col-sm-10">
                    <form:input path="sms" type="text" class="form-control" id="sms"
                                placeholder="sms amount" required="true" pattern="^[0-9]+$"/>
                    <form:errors path="sms" class="control-label"/>
                </div>
            </div>
        </spring:bind>

        <spring:bind path="minutes">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label">Minutes</label>
                <div class="col-sm-10">
                    <form:input path="minutes" type="text" class="form-control" id="minutes"
                                placeholder="minutes amount" required="true" pattern="^[0-9]+$"/>
                    <form:errors path="minutes" class="control-label"/>
                </div>
            </div>
        </spring:bind>

        <spring:bind path="internet">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label">Internet</label>
                <div class="col-sm-10">
                    <form:input path="internet" type="text" class="form-control" id="internet"
                                placeholder="amount of Gb" required="true" pattern="^[0-9]+$"/>
                    <form:errors path="internet" class="control-label"/>
                </div>
            </div>
        </spring:bind>

        <spring:bind path="price">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label">Price</label>
                <div class="col-sm-10">
                    <form:input path="price" type="text" class="form-control" id="price"
                                placeholder="0.0" required="true" pattern="^[0-9]+$"/>
                    <form:errors path="price" class="control-label"/>
                </div>
            </div>
        </spring:bind>

        <div id="options" class="form-group">
            <div id="remain" style="float: left; width: 500px">
                <label class="col-sm-2 control-label" style="font-size: x-large">Choose incompatible</label>
                <div class="col-sm-10" style="font-size: x-large">
                    <c:if test="${not empty remainAdditionList}">
                        <form:select path="addIds" multiple="true" size="">
                            <c:forEach varStatus="loop" items="${remainAdditionList}" var="add">
                                <form:option value="${add.id}">${add.name}</form:option>
                            </c:forEach>
                        </form:select>
                    </c:if>
                    <c:if test="${empty remainAdditionList}">
                        <p>No options remained</p>
                    </c:if>
                </div>
            </div>

            <c:if test="${not tariff['new']}">
                <div class="form-group" id="incomp" style="float: left; margin-left: 200px">
                    <label class="col-sm-2 control-label" style="font-size: x-large">Incompatible options</label>
                    <div class="col-sm-10">
                        <c:if test="${not empty additionList}">
                        <table>
                            <c:forEach items="${additionList}" var="pack" varStatus="loop">
                                <tr>
                                    <td style="font-size: x-large">
                                        <c:out value="${pack.addition.name}"/>
                                    </td>
                                    <td>
                                        <button type="button" class="btn btn-primary btn-lg"
                                                onclick="location.href='${contextPath}/employee/${tariff.id}/${pack.addition.id}/removeIncompatibleOption'">
                                            X
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                        </c:if>
                        <c:if test="${empty additionList}">
                            <p>No incompatible options</p>
                        </c:if>
                    </div>
                </div>
            </c:if>
        </div>

        <div class="form-group" style="margin-top: 800px">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${tariff['new']}">
                        <button type="submit" class="btn-lg btn-primary pull-right">Add</button>
                    </c:when>
                    <c:otherwise>
                        <button type="submit" class="btn-lg btn-primary pull-right">Update</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>

</div>

<jsp:include page="footer.jsp"/>

</body>
</html>