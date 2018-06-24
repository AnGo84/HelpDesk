<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:template>

    <jsp:attribute name="title">Users List</jsp:attribute>

    <jsp:body>
        <div class="generic-container">
            <div class="container">
                <!-- Default panel contents -->
                <div class="panel-heading"><span class="lead">List of Users </span></div>

                <div class="container">
                    <table id="userstable"
                           class="table table-striped table-bordered dataTable table-hover table-condensed"
                           cellspacing="0" width="100%"
                        <%--class="table table-hover"--%>
                    >
                        <thead>
                        <tr>
                            <th>Login</th>
                            <th>Lastname</th>
                            <th>Firstname</th>
                            <th>Middlename</th>
                            <th>Type</th>
                            <th>Email</th>
                            <th>Phone</th>

                            <security:authorize access="hasRole('ADMIN') or hasRole('SUPPORT')">
                                <th width="80"></th>
                            </security:authorize>
                            <security:authorize access="hasRole('ADMIN')">
                                <th width="80"></th>
                            </security:authorize>

                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${users}" var="user">
                            <tr>
                                <td>${user.login}</td>
                                <td>${user.lastName}</td>
                                <td>${user.firstName}</td>
                                <td>${user.middleName}</td>
                                <td>${user.userType.name}</td>
                                <td>${user.email}</td>
                                <td>${user.phone}</td>

                                <security:authorize access="hasRole('ADMIN') or hasRole('SUPPORT')">
                                    <td align="center"><a href="<c:url value='/edit-user-${user.login}' />"
                                           class="btn btn-success custom-width">edit</a>
                                    </td>
                                </security:authorize>
                                <security:authorize access="hasRole('ADMIN')">
                                    <td align="center"><a href="<c:url value='/delete-user-${user.login}' />"
                                           class="btn btn-danger custom-width">delete</a>
                                    </td>
                                </security:authorize>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <security:authorize access="hasRole('ADMIN')">
                    <div class="well">
                            <%--<a type="button" href="<c:url value='/newuser' />" role="button">Add New User</a>--%>
                        <a class="btn btn-info" href="<c:url value='/newuser' />" role="button" data-placement="right"
                           title="create new User">
                            <span class="glyphicon glyphicon-plus"></span>
                            Add New User</a>
                    </div>
                </security:authorize>

            </div>

        </div>

        <%--<script>--%>
        <%--$(function () {--%>
        <%--// Очистим форму чтобы при перезагрузке страницы--%>
        <%--// данные формы вернулись в исходное значение--%>
        <%--$("form").trigger("reset");--%>
        <%--// активируем плагин jQuery Chained Selects--%>
        <%--$("#subcategories").chained("#categories");--%>
        <%--//при изменении значения 1го списка:--%>
        <%--$("#categories").change(function () {--%>
        <%--$("#subcategories").selectpicker('refresh');--%>
        <%--});--%>
        <%--});--%>
        <%--</script>--%>

        <script>
            $(document).ready(function () {
                // DataTable
                $('#userstable').DataTable(
                    {
                        <%--"language": {--%>
                        <%--//"lengthMenu": "Display _MENU_ records per page",--%>
                        <%--&lt;%&ndash;"lengthMenu": '<spring:message code="table.lengthMenu" javaScriptEscape="true" />',&ndash;%&gt;--%>
                        <%--},--%>
                        "lengthMenu": [[10, 15, 20, 25, 50, -1], [10, 15, 20, 25, 50, "All"]],
                        "iDisplayLength": 15,
                        "order": [[0, "asc"]],
                        columnDefs: [
                            {orderable: false, targets: [-1, -2]}
                        ]
                    }
                );

            });
        </script>

    </jsp:body>

</page:template>
