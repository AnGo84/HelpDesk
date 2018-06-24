<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Login page</title>
    <link rel="shortcut icon" href="/static/images/logo_16.ico" type="image/x-icon">

    <link href="<c:url value='/static/css/bootstrap.min.css' />" rel="stylesheet"></link>
    <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>

</head>

<body>
<div id="mainWrapper">
    <div class="login-container">
        <div class="login-card">
            <div class="login-form">
                <c:url var="loginUrl" value="/login"/>
                <form action="${loginUrl}" method="post" class="form-horizontal">
                    <c:if test="${param.error != null}">
                        <div class="alert alert-danger">
                            <p>Invalid username and password.</p>
                        </div>
                    </c:if>
                    <c:if test="${param.logout != null}">
                        <div class="alert alert-success">
                            <p>You have been logged out successfully.</p>
                        </div>
                    </c:if>
                    <%--User name--%>
                    <div class="input-group input-sm">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>

                        <%--<label class="input-group-addon" for="username"><i class="f fa-user"></i></label>--%>
                        <input type="text" class="form-control" id="username" name="login" placeholder="Enter Username"
                               required>
                    </div>
                    <%--Pass--%>
                    <div class="input-group input-sm">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                        <%--<label class="input-group-addon" for="password"><i class="fa fa-lock"></i></label>--%>
                        <input type="password" class="form-control" id="password" name="password"
                               placeholder="Enter Password" required>
                    </div>
                    <%--Remember me--%>
                    <div class="input-group input-sm">
                        <div class="checkbox">
                            <label><input type="checkbox" id="rememberme" name="remember-me"> Remember Me</label>
                        </div>
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                    <div class="form-actions">
                        <input type="submit"
                               class="btn btn-block btn-primary btn-default" value="Log in">
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%--<div class="container">--%>
    <%--<div class="card card-container">--%>
        <%----%>
        <%--<form class="form-signin">--%>
            <%--<span id="reauth-email" class="reauth-email"></span>--%>
            <%--<input type="email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>--%>
            <%--<input type="password" id="inputPassword" class="form-control" placeholder="Password" required>--%>
            <%--<div id="remember" class="checkbox">--%>
                <%--<label>--%>
                    <%--<input type="checkbox" value="remember-me"> Remember me--%>
                <%--</label>--%>
            <%--</div>--%>
            <%--<button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Sign in</button>--%>
        <%--</form><!-- /form -->--%>
        <%--<a href="#" class="forgot-password">--%>
            <%--Forgot the password?--%>
        <%--</a>--%>
    <%--</div><!-- /card-container -->--%>
<%--</div><!-- /container -->--%>

</body>
</html>