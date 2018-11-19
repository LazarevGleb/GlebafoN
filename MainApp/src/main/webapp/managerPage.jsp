<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<head>
    <title>Manager Page</title>
    <jsp:include page="header.jsp"/>
    <link href='https://fonts.googleapis.com/css?family=Merienda' rel='stylesheet'>
    <style>
        ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            overflow: hidden;
            background-color: #17a2b8;
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

    <style>
        .bg {
            background-image: url("resources/images/back.jpeg");
            height: 1000px;
            background-position: center;
            background-repeat: no-repeat;
            background-size: cover;
        }
        .bg p{
            position: relative;
            top: 200px;
        }
        #gleb{
            font-family: 'Merienda';
            font-size: 150px;
            color: #003eff;
            position: relative;
            top: 300px;
        }
    </style>
</head>
<body>
<c:set value="${pageContext.request.contextPath}" var="contextPath"/>
<div>
    <ul>
        <li>
            <form action="${contextPath}/employee/showAllClients" method="GET">
                <button type="submit" class="btn-lg">Clients</button>
            </form>
        </li>
        <li>
            <form action="${contextPath}/employee/showAllContracts" method="get">
                <button type="submit" class="btn-lg">Contracts</button>
            </form>
        </li>
        <li>
            <form action="${contextPath}/employee/tariffs" method="get">
                <button type="submit" class="btn-lg">Tariffs</button>
            </form>
        </li>
        <li>
            <form action="${contextPath}/employee/additions" method="get">
                <button type="submit" class="btn-lg">Options</button>
            </form>
        </li>
        <li>
            <form action="${contextPath}/login" method="get">
                <button type="submit" class="btn-lg">Quit</button>
            </form>
        </li>
    </ul>
</div>

<div id="mainDiv" class="bg text-center display-4">
    <p>You are on manager's page!</p>
    <p id="gleb">GlebafoN</p>
</div>
<jsp:include page="footer.jsp"/>
<script src="<c:url value="/resources/scripts/manager.js"/> "></script>
</body>
</html>
