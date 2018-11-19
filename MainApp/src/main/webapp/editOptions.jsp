<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Options</title>
    <jsp:include page="header.jsp"/>
</head>
<body>
<c:set value="${pageContext.request.contextPath}" var="contextPath"/>
<div style="margin-left: 50px; margin-top: 20px; margin-right: 50px">
    <form action="${contextPath}/employee/showAllContracts" method="get">
        <button type="submit" class="btn btn-primary btn-lg">Return</button>
    </form>
    <table style="margin-top: 100px">
        <c:forEach items="${contract.additions}" var="add" varStatus="loop">
            <tr>
                <td style="font-size: x-large">
                    <c:out value="${add.name}"/>
                </td>
                <td>
                    <form action="${contextPath}/employee/${contract.id}/${add.id}/removeOption" method="post">
                        <button type="submit" class="btn btn-primary btn-lg"> X</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<div style="margin-left: 50px; margin-top: 20px; margin-right: 50px; font-size: x-large">
    <form:form modelAttribute="expense" method="post"
               action="${contextPath}/employee/${contract.id}/editOptions">
        <c:if test="${not empty expense.options}">
            <form:select path="optionIds" multiple="true" size="">
                <c:forEach items="${expense.options}" var="opt">
                    <form:option value="${opt.id}">${opt.name}</form:option>
                </c:forEach>
            </form:select>
            <button type="submit" class="btn btn-primary btn-lg">Add</button>
        </c:if>
    </form:form>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
