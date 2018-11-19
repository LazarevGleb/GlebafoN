<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Clients List</title>
    <jsp:include page="header.jsp"/>
    <style>
        ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            overflow: hidden;
        }

        li {
            float: left;
            margin-left: 10px;
            margin-top: 12px;
        }

        button {
            cursor: pointer;
        }

        li a {
            display: block;
            color: white;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
        }

        li a:hover {
            background-color: #111;
        }
    </style>
</head>
<body>
<c:set value="${pageContext.request.contextPath}" var="contextPath"/>
<div style="margin-left: 50px; margin-top: 20px; margin-right: 50px">
    <ul>
        <li>
            <form method="get" action="${contextPath}/employee/newClient">
                <button type="submit" class="btn btn-primary btn-lg">New client</button>
            </form>
        </li>
        <li>
            <form method="get" action="${contextPath}/employee/findClient">
                <button type="submit" class="btn btn-primary btn-lg">Find</button>
            </form>
        </li>
        <li>
            <form method="get" action="${contextPath}/employee">
                <button type="submit" class="btn btn-primary btn-lg">Return</button>
            </form>
        </li>
    </ul>

    <table class="table table-striped text-center" style="font-size: large">
        <tr class="thead-dark">
            <th>Id</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Birthday</th>
            <th>Passport</th>
            <th>Address</th>
            <th>Email</th>
            <th>Contracts</th>
            <th>Blocked status</th>
        </tr>

        <c:forEach items="${clientsList}" var="client">
            <tr>
                <td>${client.id}</td>
                <td>${client.name}</td>
                <td>${client.surname}</td>
                <td>${client.birthday}</td>
                <td>
                        ${client.passportSeries}${' '}${client.passportNumber}${'; '}<br/>
                    выдан: ${client.passportIssuedBy}${', '}${client.passportIssueDate}${'; '}
                        ${client.passportDivisionCode}
                </td>
                <td>${client.address}</td>
                <td>${client.email}</td>
                <td>
                    <c:forEach items="${client.contracts}" var="cnt" varStatus="loop">
                        <c:out value="${cnt.number}"/>
                        <c:if test="${not loop.last}">,<br/></c:if>
                    </c:forEach>
                </td>
                <td>
                    <c:forEach items="${client.contracts}" var="cnt" varStatus="loop">
                        <c:choose>
                            <c:when test="${cnt.block eq 'CLIENT_BLOCKED'}">
                                <b><c:out value="blocked by client"/></b>
                                <c:if test="${not loop.last}">,<br/></c:if>
                            </c:when>
                            <c:when test="${cnt.block eq 'MANAGER_BLOCKED'}">
                                <b><c:out value="blocked by manager"/></b>
                                <c:if test="${not loop.last}">,<br/></c:if>
                            </c:when>
                            <c:otherwise>
                                <c:out value="not blocked"/>
                                <c:if test="${not loop.last}">,<br/></c:if>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </td>
                <td>
                    <form action="${contextPath}/employee/${client.id}/addNumber" method="get">
                        <button type="submit" class="btn btn-primary btn-lg">Add number</button>
                    </form>
                    <form action="${contextPath}/employee/${client.id}/deleteClient" method="post">
                        <button type="submit" class="btn btn-primary btn-lg">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
