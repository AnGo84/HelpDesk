
<%--<c:set var="now" value="<%=new java.util.Date()%>"/>--%>

<nav class="navbar navbar-default">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="/index">HelpDesk</a>
        </div>
        <ul class="nav navbar-nav">
            <%--<li class="active">--%>
            <%--<a href="#">Home</a>--%>
            <%--</li>--%>
                            <%--<li>--%>
                    <%--<a href="/userslist">Users</a>--%>
                <%--</li>--%>

                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Services
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/userslist">Users</a></li>
                        <li><a href="#">Groups</a></li>
                    </ul>
                </li>



            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">Services
                    <span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <li><a href="/serviceslist">Services</a></li>
                    <li><a href="/categorieslist">Categories</a></li>
                </ul>
            </li>


        </ul>
        <%--<ul class="nav navbar-nav">--%>
        <%--<span class="navbar-text">--%>
        <%--&lt;%&ndash;Navbar text with an inline element&ndash;%&gt;--%>
        <%--<fmt:formatDate value="${now}" type="date" pattern="dd MMMM yyyy"/>--%>
        <%--</span>--%>
        <%--</ul>--%>
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
</nav>


<%--<div class="authbar">--%>
<%--<span>Dear <strong>${loggedinuser}</strong>, Welcome to CrazyUsers.</span> <span class="floatRight"><a--%>
<%--href="<c:url value="/logout" />">Logout</a></span>--%>
<%--</div>--%>
