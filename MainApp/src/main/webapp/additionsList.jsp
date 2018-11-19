<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <title>Additions</title>
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
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<div style="margin-left: 50px; margin-top: 20px; margin-right: 50px">
    <ul>
        <li>
            <form method="get" action="${contextPath}/employee/additions/add">
                <button type="submit" class="btn btn-primary btn-lg">New option</button>
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
            <th>Parameter</th>
            <th>Value</th>
            <th>Price</th>
            <th>Activation cost</th>
            <th>Incompatible tariffs</th>
            <th>Mandatory options</th>
            <th>Incompatible options</th>
        </tr>

        <c:forEach var="add" items="${additionList}">
            <tr>
                <td>${add.id}</td>
                <td>${add.name}</td>
                <td>${add.parameter}</td>
                <td>${add.value}</td>
                <td>${add.price}</td>
                <td>${add.additionActivationCost}</td>
                <td>
                    <c:if test="${empty add.packages}">
                        <c:out value="-"/>
                    </c:if>
                    <c:if test="${not empty add.packages}">
                        <c:forEach items="${add.packages}" var="package" varStatus="loop">
                            <c:out value="${package.tariff.name} (${package.tariff.description})"/>
                            <c:if test="${not loop.last}">,</c:if>
                        </c:forEach>
                    </c:if>
                </td>
                <td>
                    <c:if test="${empty add.mandatoryOptions}">
                        <c:out value="-"/>
                    </c:if>
                    <c:if test="${not empty add.mandatoryOptions}">
                        <c:forEach items="${add.mandatoryOptions}" var="addition" varStatus="loop">
                           <c:if test="${addition.valid}">
                               <c:out value="${addition.name}"/>
                           </c:if>
                            <c:if test="${not loop.last}">,</c:if>
                        </c:forEach>
                    </c:if>
                </td>
                <td>
                    <c:if test="${empty add.incompatibleOptions}">
                        <c:out value="-"/>
                    </c:if>
                    <c:if test="${not empty add.incompatibleOptions}">
                        <c:forEach items="${add.incompatibleOptions}" var="addition" varStatus="loop">
                            <c:if test="${addition.valid}">
                                <c:out value="${addition.name}"/>
                            </c:if>
                            <c:if test="${not loop.last}">,</c:if>
                        </c:forEach>
                    </c:if>
                </td>
                <td>
                    <form action="${contextPath}/employee/additions/${add.id}/update" method="post">
                        <input type="submit" value="Update"/>
                    </form>
                    <form action="${contextPath}/employee/additions/${add.id}/delete" method="post">
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