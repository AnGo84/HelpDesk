<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:template>

    <jsp:attribute name="title">Groups List</jsp:attribute>

    <jsp:body>
        <div class="generic-container">
            <div class="container">
                <!-- Default panel contents -->
                <div class="panel-heading"><span class="lead">List of Groups </span></div>

                <div class="container">
                    <table id="groupstable"
                           class="table table-striped table-bordered dataTable table-hover table-condensed"
                           cellspacing="0" width="100%"
                        <%--class="table table-hover"--%>
                    >
                        <thead>
                        <tr>

                            <th width="50" align="center">ID</th>
                            <th align="center">Name</th>
                            <th width="80" align="center">Right</th>

                            <security:authorize access="hasRole('ADMIN') or hasRole('SUPPORT')">
                                <th width="80"></th>
                            </security:authorize>
                            <security:authorize access="hasRole('ADMIN') or hasRole('SUPPORT')">
                                <th width="80"></th>
                            </security:authorize>

                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${groups}" var="group">
                            <tr>
                                <td align="center">${group.id}</td>
                                <td>${group.name}</td>
                                <td align="center">
                                    <c:choose>
                                        <c:when test="${group.right}">
                                            SUPPORT
                                        </c:when>
                                        <c:otherwise>
                                            USER
                                        </c:otherwise>
                                    </c:choose>

                                </td>

                                <security:authorize access="hasRole('ADMIN') or hasRole('SUPPORT')">
                                    <td align="center"><a href="<c:url value='/edit-group-${group.id}' />"
                                                          class="btn btn-success custom-width">edit</a>
                                    </td>
                                </security:authorize>
                                <security:authorize access="hasRole('ADMIN') or hasRole('SUPPORT')">
                                    <td align="center"><a href="<c:url value='/deletegroup-${group.id}' />"
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

                        <a class="btn btn-info" href="<c:url value='/newgroup' />" role="button" data-placement="right"
                           title="create new Group">
                            <span class="glyphicon glyphicon-plus"></span>
                            Add New Group</a>
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
                $('#groupstable').DataTable(
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
