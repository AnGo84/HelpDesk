<!DOCTYPE html>
<%@tag description="Template Site Header tag" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:set var="now" value="<%=new java.util.Date()%>"/>
<%--<c:url value="/index" var="file"/>--%>
<%--<c:url value="/jdbc.html" var="jdbc"/>--%>
<%--<c:url value="/email.html" var="email" />--%>


<nav class="navbar navbar-default" role="navigation">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <%--<div class="navbar-header">--%>
        <%--<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#main_navbar">--%>
        <%--<span class="sr-only">Toggle navigation</span>--%>
        <%--<span class="icon-bar"></span>--%>
        <%--<span class="icon-bar"></span>--%>
        <%--<span class="icon-bar"></span>--%>
        <%--</button>--%>
        <%--<a class="navbar-brand" href="index.html">Home</a>--%>
        <%--</div>--%>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="main_navbar">

            <div class="navbar-header">
                <a class="navbar-brand" href="/index">HelpDesk</a>
            </div>
            <ul class="nav navbar-nav">
                <%--<li class="active">--%>
                <%--<a href="#">Home</a>--%>
                <%--</li>--%>
                <security:authorize access="hasRole('ADMIN') or hasRole('SUPPORT')">
                    <%--<li>--%>
                    <%--<a href="/userslist">Users</a>--%>
                    <%--</li>--%>

                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Users
                            <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="/userslist">Users list</a></li>
                            <li class="divider"></li>
                            <%--<li><a href="#">Groups categories</a></li>--%>
                            <li><a href="/groupslist">Groups list</a></li>
                        </ul>
                    </li>
                </security:authorize>


                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Services
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/serviceslist">Services list</a></li>
                        <li><a href="/categorieslist">Categories list</a></li>
                    </ul>
                </li>

            </ul>
            <ul class="nav navbar-nav">
                <p  class="navbar-text" align="center">
                </p>
            </ul>
            <ul class="nav navbar-nav">
                <p  class="navbar-text" align="center"> Today is <strong>
                    <fmt:formatDate value="${now}" type="date" pattern="dd MMMM yyyy"/>
                </strong>
                </p>
            </ul>
            <%-- User info--%>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="#">
                        <span class="glyphicon glyphicon-user"/> <strong> ${loggedinuser} </strong> </a>
                </li>
                <li>
                    <a href="<c:url value="/logout" />">
                        <span class="glyphicon glyphicon-log-in"/> Logout </a>
                </li>
            </ul>
        </div>
    </div>
</nav>

