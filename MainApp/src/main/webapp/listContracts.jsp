<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Contracts List</title>
    <jsp:include page="header.jsp"/>
</head>
<body>
<c:set value="${pageContext.request.contextPath}" var="contextPath"/>
<div style="margin-left: 50px; margin-top: 20px; margin-right: 50px">
    <form:form method="get" action="${contextPath}/employee">
        <button type="submit" class="btn btn-primary btn-lg">Return</button>
    </form:form>
    <table class="table table-striped text-center" style="font-size: large">
        <tr class="thead-dark">
            <th>Id</th>
            <th>Number</th>
            <th>Client name</th>
            <th>Client surname</th>
            <th>Balance</th>
            <th>Tariff</th>
            <th>Chosen Options</th>
            <th>Blocked status</th>
        </tr>
        <c:forEach items="${contractList}" var="cnt">
            <tr>
                <td>${cnt.id}</td>
                <td>${cnt.number}</td>
                <td>${cnt.client.name}</td>
                <td>${cnt.client.surname}</td>
                <td>${cnt.balance}</td>
                <td>${cnt.tariff.name}</td>
                <td>
                    <c:if test="${empty cnt.additions}">
                        <c:out value="-"/>
                    </c:if>
                    <c:if test="${not empty cnt.additions}">
                        <c:forEach items="${cnt.additions}" var="add" varStatus="loop">
                            <c:out value="${add.name}"/>
                            <c:if test="${not loop.last}">,</c:if>
                        </c:forEach>
                    </c:if>

                </td>
                <td>
                    <c:choose>
                        <c:when test="${cnt.block eq 'CLIENT_BLOCKED'}">
                            <b><c:out value="blocked by client"/></b>
                        </c:when>
                        <c:when test="${cnt.block eq 'MANAGER_BLOCKED'}">
                            <b><c:out value="blocked by manager"/></b>
                        </c:when>
                        <c:otherwise>
                            <c:out value="not blocked"/>
                        </c:otherwise>
                    </c:choose>
                </td>

                <td>
                    <div class="dropdown">
                        <button class="btn btn-primary dropdown-toggle" type="button"
                                data-toggle="dropdown">Actions <span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <li>
                                <form action="${contextPath}/employee/${cnt.id}/block" method="post">
                                    <c:choose>
                                        <c:when test="${cnt.block eq 'CLIENT_BLOCKED' or cnt.block eq 'MANAGER_BLOCKED'}">
                                            <button type="submit" class="btn btn-primary btn-sm">Unblock</button>
                                        </c:when>
                                        <c:otherwise>
                                            <button type="submit" class="btn btn-primary btn-sm">Block</button>
                                        </c:otherwise>
                                    </c:choose>
                                </form>
                            </li>
                            <li>
                                <form action="${contextPath}/employee/${cnt.id}/balance" method="get">
                                    <button type="submit" class="btn btn-primary btn-sm">Balance</button>
                                </form>
                            </li>
                            <li>
                                <form action="${contextPath}/employee/${cnt.id}/changeNumber" method="get">
                                    <button type="submit" class="btn btn-primary btn-sm">Number</button>
                                </form>
                            </li>
                            <li>
                                <form action="${contextPath}/employee/${cnt.id}/changeTariff" method="get">
                                    <button type="submit" class="btn btn-primary btn-sm">Tariff</button>
                                </form>
                            </li>
                            <li>
                                <form action="${contextPath}/employee/${cnt.id}/editOptions" method="get">
                                    <button type="submit" class="btn btn-primary btn-sm">Options</button>
                                </form>
                            </li>
                            <li>
                                <c:choose>
                                    <c:when test="${cnt.block eq 'MANAGER_BLOCKED'}">
                                        <form action="${contextPath}/employee/${cnt.id}/deleteContract" method="post">
                                            <button type="submit" class="btn btn-primary btn-sm">Delete</button>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <form action="${contextPath}/employee/${cnt.id}/deleteContract" method="post">
                                            <button type="submit" class="btn btn-primary btn-sm" disabled>Delete</button>
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </li>
                        </ul>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
