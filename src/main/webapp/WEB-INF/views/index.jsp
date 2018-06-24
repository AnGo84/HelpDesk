<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page isELIgnored="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:template>
    <jsp:attribute name="title">HelpDesk</jsp:attribute>

    <jsp:body>
        <c:set var="now" value="<%=new java.util.Date()%>"/>
        <!-- Page Content -->
        <div class="generic-container">
            <div class="container">
                <!--  -->

                    <%--<div class="row">--%>
                    <%--<div class="col-sm-4">--%>
                    <%--<button type="button" class="btn btn-info" data-toggle="modal"--%>
                    <%--data-placement="right" title="create new task" data-target="#addTask">--%>
                    <%--<span class="glyphicon glyphicon-plus"/>--%>
                    <%--</span>--%>
                    <%--Add New Task--%>
                    <%--</button>--%>
                    <%--&lt;%&ndash;style="width:100px"&ndash;%&gt;--%>
                    <%--</div>--%>
                    <%--</div>--%>
                    <%--<br>--%>

                <div class="well">
                    <a class="btn btn-info" href="<c:url value='/newticket' />" role="button" data-placement="right"
                       title="create new Task">
                        <span class="glyphicon glyphicon-plus"></span>
                        Add New Task</a>
                </div>

                <!-- Table block -->
                <div>
                    <div class="container">
                        <table id="ticketsTable"
                               class="table table-striped table-bordered dataTable table-hover table-condensed"
                               cellspacing="0" width="100%"
                            <%--class="table table-hover"--%>
                        >
                            <thead>
                            <tr>
                                    <%--<th>Id</th>--%>
                                <th>Number</th>
                                <th>Service</th>
                                <th>Category</th>
                                <th>Theme</th>
                                <th>Date</th>
                                <th>State</th>
                                <th>User</th>
                                <th>Performer</th>

                                    <%--<security:authorize access="hasRole('ADMIN') or hasRole('SUPPORT')">--%>
                                    <%--<th width="80"></th>--%>
                                    <%--</security:authorize>--%>
                                    <%--<security:authorize access="hasRole('ADMIN')">--%>
                                    <%--<th width="80"></th>--%>
                                    <%--</security:authorize>--%>

                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${tickets}" var="ticket">
                                <tr>
                                    <td><a href="<c:url value='/edit-ticket-${ticket.id}' />">${ticket.number}</a></td>
                                    <td>${ticket.service.name}</td>
                                    <td>${ticket.category.name}</td>
                                    <td>${ticket.theme}</td>
                                    <td>${ticket.date}</td>
                                    <td>${ticket.ticketState.name}</td>
                                    <td>${ticket.user.login}</td>
                                    <td>${ticket.performer.login}</td>

                                        <%--<security:authorize access="hasRole('ADMIN') or hasRole('SUPPORT')">--%>
                                        <%--<td align="center"><a href="<c:url value='/edit-user-${user.login}' />"--%>
                                        <%--class="btn btn-success custom-width">edit</a>--%>
                                        <%--</td>--%>
                                        <%--</security:authorize>--%>
                                        <%--<security:authorize access="hasRole('ADMIN')">--%>
                                        <%--<td align="center"><a href="<c:url value='/delete-user-${user.login}' />"--%>
                                        <%--class="btn btn-danger custom-width">delete</a>--%>
                                        <%--</td>--%>
                                        <%--</security:authorize>--%>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>

                </div>


            </div>
        </div>
        </div>

        <!-- Go to TOP -->
        <a id="back-to-top" href="#" class="btn btn-default back-to-top" role="button" title="Back to Top"
           data-toggle="tooltip"
           data-placement="top">
            <span class="glyphicon glyphicon-chevron-up"/>
        </a>
        <!--End Go to TOP -->
        <!--</div></div> -->

        <%--Table--%>
        <script>
            $(document).ready(function () {
                // DataTable
                $('#ticketsTable').DataTable(
                    {
                        <%--"language": {--%>
                        <%--//"lengthMenu": "Display _MENU_ records per page",--%>
                        <%--&lt;%&ndash;"lengthMenu": '<spring:message code="table.lengthMenu" javaScriptEscape="true" />',&ndash;%&gt;--%>
                        <%--},--%>
                        "lengthMenu": [[10, 15, 20, 25, 50, -1], [10, 15, 20, 25, 50, "All"]],
                        "iDisplayLength": 15
                    }
                );

            });
        </script>

    </jsp:body>
</page:template>

