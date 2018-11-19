<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Create an account</title>
<jsp:include page="header.jsp"/>

</head>

<body>

<div class="container">

    <form:form method="POST" modelAttribute="user" class="form-signin"
               action="${contextPath}/registration">
        <h2 class="form-signin-heading">Create your account</h2>
        <spring:bind path="number">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="number" class="form-control" placeholder="+7-XXX-XXX-XXXX"
                            pattern="\+7-[0-9]{3}-[0-9]{3}-[0-9]{4}"
                            autofocus="true"/>
                <form:errors path="number"/>
            </div>
        </spring:bind>

        <spring:bind path="password">
            <div class="form-group ${status.error ? 'has-error' : ''}" id="pasIn">
                <form:input type="password" path="password" class="form-control"
                            placeholder="Password should have at least 8 characters: numbers or letters"
                pattern="[a-zA-z0-9]{8,}"/>
                <form:errors path="password"/>
            </div>
        </spring:bind>

        <spring:bind path="confirmPassword">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="password" path="confirmPassword" class="form-control"
                            pattern="[a-zA-z0-9]{8,}"
                            placeholder="Confirm your password" tooltip="Passwords must be the same!"/>
                <form:errors path="confirmPassword"/>
            </div>
        </spring:bind>

        <button class="btn btn-lg btn-primary btn-block" id="registrButton" type="submit">Submit</button>
    </form:form>

</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
