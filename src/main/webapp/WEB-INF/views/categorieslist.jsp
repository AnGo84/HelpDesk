<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:template>

    <jsp:attribute name="title">Categories List</jsp:attribute>

    <jsp:body>
        <div class="generic-container">
            <div class="container">
                <!-- Default panel contents -->
                <div class="panel-heading"><span class="lead">List of Categories </span></div>

                <div class="container">
                    <table id="categoriestable"
                           class="table table-striped table-bordered dataTable table-hover table-condensed"
                           cellspacing="0" width="100%"
                        <%--class="table table-hover"--%>
                    >
                        <thead>
                        <tr>
                            <th width="50" align="center">ID</th>
                            <th>Service name</th>
                            <th>Name</th>

                            <security:authorize access="hasRole('ADMIN') or hasRole('SUPPORT')">
                                <th width="80"></th>
                            </security:authorize>
                            <security:authorize access="hasRole('ADMIN') or hasRole('SUPPORT')">
                                <th width="80"></th>
                            </security:authorize>

                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${categories}" var="category">
                            <tr>
                                <td align="center">${category.id}</td>
                                <td>${category.service.name}</td>
                                <td>${category.name}</td>

                                <security:authorize access="hasRole('ADMIN') or hasRole('SUPPORT')">
                                    <td align="center"><a href="<c:url value='/edit-category-${category.id}' />"
                                           class="btn btn-success custom-width">edit</a>
                                    </td>
                                </security:authorize>
                                <security:authorize access="hasRole('ADMIN') or hasRole('SUPPORT')">
                                    <td align="center"><a href="<c:url value='/delete-category-${category.id}' />"
                                           class="btn btn-danger custom-width">delete</a>
                                    </td>
                                </security:authorize>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <security:authorize access="hasRole('ADMIN') or hasRole('SUPPORT')">
                    <div class="well">
                        <a class="btn btn-info" href="<c:url value='/newcategory' />" role="button" data-placement="right"
                           title="create new Category">
                            <span class="glyphicon glyphicon-plus"></span>
                            Add New Category</a>
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
        <%--//при изменении значения 1го списка:--%>
        <%--$("#categories").change(function () {--%>
        <%--$("#subcategories").selectpicker('refresh');--%>
        <%--});--%>
        <%--});--%>
        <%--</script>--%>
        <%--$("#subcategories").chained("#categories");--%>

        <script>
            $(document).ready(function () {
                // DataTable
                $('#categoriestable').DataTable(
                    {
                        <%--"language": {--%>
                        <%--//"lengthMenu": "Display _MENU_ records per page",--%>
                        <%--&lt;%&ndash;"lengthMenu": '<spring:message code="table.lengthMenu" javaScriptEscape="true" />',&ndash;%&gt;--%>
                        <%--},--%>
                        "lengthMenu": [[10, 15, 20, 25, 50, -1], [10, 15, 20, 25, 50, "All"]],
                        "iDisplayLength": 15,
                        "order": [[1, "asc"]],
                        columnDefs: [
                            {orderable: false, targets: [-1, -2]}
                        ]
                    }
                );

            });
        </script>

    </jsp:body>

</page:template>
