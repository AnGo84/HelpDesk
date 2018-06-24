<!DOCTYPE html>
<%@tag description="Template Site tag" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="headerTemplate" tagdir="/WEB-INF/tags" %>

<%@attribute name="title" fragment="true" %>
<%@attribute name="header" fragment="true" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <title>
        <jsp:invoke fragment="title"/>
    </title>

    <!-- add icon for sait http://htmlbook.ru/faq/kak-dobavit-ikonku-sayta-v-adresnuyu-stroku-brauzera -->
    <link rel="shortcut icon" href="/static/images/logo_16.ico" type="image/x-icon">
    <%--<meta name="viewport" content="width=device-width, initial-scale=1.0">--%>

    <!-- Bootstrap -->
    <link href="/static/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="/static/css/bootstrap-select.min.css" rel="stylesheet">
    <link href="/static/css/theme.css" rel="stylesheet">
    <link href="/static/css/dataTables.bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="/static/css/helpdesk.css" rel="stylesheet" media="screen">
    <%--<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>--%>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="/static/js/jquery_latest.js"></script>

    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="/static/js/bootstrap.min.js"></script>
    <script src="/static/js/bootstrap-select.min.js"></script>
    <script src="/static/js/jquery.chained.min.js"></script>
    <script src="/static/js/jquery.dataTables.min.js"></script>
    <script src="/static/js/dataTables.bootstrap.min.js"></script>
    <!-- JS from author of HelpDeks-->
    <script src="/static/js/helpdesk.js"></script>


    <%--<!-- Bootstrap Core CSS -->--%>
    <%--<spring:url value="/WEB-INF/static/css/bootstrap.css" var="bootstrap"/>--%>
    <%--<link href="${bootstrap}" rel="stylesheet"/>--%>

    <%--<!-- Custom Fonts -->--%>
    <%--<spring:url value="/resources/font-awesome/css/font-awesome.min.css" var="fontawesome"/>--%>
    <%--<link href="${fontawesome}" rel="stylesheet"/>--%>

    <%--<!-- jQuery -->--%>
    <%--<spring:url value="/resources/js/jquery-2.1.4.min.js" var="jqueryjs"/>--%>
    <%--<script src="${jqueryjs}"></script>--%>

    <%--<!-- Bootstrap Core JavaScript -->--%>
    <%--<spring:url value="/resources/js/bootstrap.min.js" var="js"/>--%>
    <%--<script src="${js}"></script>--%>
</head>

<body>

<headerTemplate:header-template/>

<jsp:doBody/>

<div class="container">
    <hr>
    <!-- FOOTER -->
    <footer id="footer" class="footer text-center">
        <%--navbar-fixed-bottom--%>
        <!--<img src="resources/bootstrap/images/logo_full.jpg" class="img-rounded" alt="Logo of bank"> -->
        <h4>
            <p>Link to <a href="https://github.com/AnGo84" target="_blank">GitHub</a>
            </p>
            <p>
                <small>Designed and developed by AnGo</small>
            </p>
        </h4>
    </footer>
</div>

<script>
    if ($(document).height() <= $(window).height())
        $("footer.footer").addClass("navbar-fixed-bottom");
</script>

</html>