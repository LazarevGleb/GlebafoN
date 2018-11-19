<%@ page session="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html lang="en">
<head>
    <title>Tariffs</title>
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
            <form method="get" action="${contextPath}/employee/tariffs/add">
                <button type="submit" class="btn btn-primary btn-lg">New tariff</button>
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
            <th>SMS</th>
            <th>Minutes</th>
            <th>Internet</th>
            <th>Price</th>
            <th>Incompatible options</th>
        </tr>
        <c:forEach var="tariff" items="${tariffList}">
            <tr>
                <td>${tariff.id}</td>
                <td>${tariff.name}</td>
                <td>${tariff.sms}</td>
                <td>${tariff.minutes}</td>
                <td>${tariff.internet}${' Gb'}</td>
                <td>${tariff.price}</td>
                <td>
                    <c:if test="${empty tariff.packages}">
                        <c:out value="-"/>
                    </c:if>
                    <c:forEach var="package" items="${tariff.packages}" varStatus="loop">
                        <c:out value="${package.addition.name}"/>
                        <c:if test="${not loop.last}">,</c:if>
                    </c:forEach>
                </td>
                <td>
                    <form action="${contextPath}/employee/tariffs/${tariff.id}/update" method="post">
                        <input type="submit" value="Update"/>
                    </form>
                    <form action="${contextPath}/employee/tariffs/${tariff.id}/delete" method="post">
                        <input type="submit" value="Delete"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>