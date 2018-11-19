<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="header.jsp" />
<body>
<c:set value="${pageContext.request.contextPath}" var="contextPath"/>
<div class="container">

    <h1>Tariff details</h1>
    <br />

    <div class="row">
        <label class="col-sm-2">ID</label>
        <div class="col-sm-10">${tariff.id}</div>
    </div>

    <div class="row">
        <label class="col-sm-2">Name</label>
        <div class="col-sm-10">${tariff.name}</div>
    </div>

    <div class="row">
        <label class="col-sm-2">Description</label>
        <div class="col-sm-10">${tariff.description}</div>
    </div>

    <div class="row">
        <label class="col-sm-2">Additions</label>
        <div class="col-sm-10">
            <c:forEach var="package" items="${tariff.packages}" varStatus="loop">
                <c:out value="${package.addition.name}"/>
                <c:if test="${not loop.last}">,</c:if>
            </c:forEach>
        </div>
    </div>
</div>

<form method="get" action="${contextPath}/employee">
    <input type="submit" value="Return"/>
</form>

<jsp:include page="footer.jsp" />

</body>
</html>