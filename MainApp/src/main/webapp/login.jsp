<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href='https://fonts.googleapis.com/css?family=Merienda' rel='stylesheet'>
    <title>Glebafon</title>

    <link href="webjars/bootstrap/4.1.3/css/bootstrap.min.css" rel="stylesheet">
<style>
    #gl{
        font-family: 'Merienda';
        font-size: 120px;
        color: #003eff;
    }
</style>
</head>

<body>
<div class="container">

    <form method="POST" action="${contextPath}/login" class="form-signin">
        <h1 class="text-center">Welcome to <p id="gl">GlebafoN</p></h1>
        <h2 class="form-heading">Log in</h2>
        <div class="form-group ${error != null ? 'has-error' : ''}">
            <input name="number" type="text" class="form-control" placeholder="Phone number"
                   autofocus="autofocus"/>
            <input name="password" type="password" class="form-control"/>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Log In</button>
            <h4 class="text-center"><a href="${contextPath}/createAccountForm">Create an account</a></h4>
        </div>

    </form>

</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="webjars/jquery/3.3.1-1/jquery.min.js"></script>
<script src="webjars/bootstrap/4.1.3/js/bootstrap.min.js"></script>
</body>
</html>
