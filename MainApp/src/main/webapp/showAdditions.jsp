<%@ page session="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="header.jsp" />
<body>
<c:set value="${pageContext.request.contextPath}" var="contextPath"/>
<div class="container">

    <h1>Option details</h1>
    <br/>

    <div class="row">
        <label class="col-sm-2">ID</label>
        <div class="col-sm-10">${addition.id}</div>
    </div>

    <div class="row">
        <label class="col-sm-2">Name</label>
        <div class="col-sm-10">${addition.name}</div>
    </div>

    <div class="row">
        <label class="col-sm-2">Parameter</label>
        <div class="col-sm-10">${addition.parameter}</div>
    </div>

    <div class="row">
        <label class="col-sm-2">Value</label>
        <div class="col-sm-10">${addition.value}</div>
    </div>

    <div class="row">
        <label class="col-sm-2">Price</label>
        <div class="col-sm-10">${addition.price}</div>
    </div>

    <div class="row">
        <label class="col-sm-2">Activation cost</label>
        <div class="col-sm-10">${addition.additionActivationCost}</div>
    </div>
</div>

<form method="get" action="${contextPath}/employee">
    <input type="submit" value="Return"/>
</form>
<jsp:include page="footer.jsp"/>
</body>
</html>
