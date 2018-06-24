<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:template>

    <jsp:attribute name="title">Category Form</jsp:attribute>

    <jsp:body>
        <div class="generic-container">
            <div class="container">
                <div class="well lead">Category Registration Form</div>
                <form:form method="POST" modelAttribute="category" class="form-horizontal">
                    <form:input type="hidden" path="id" id="id"/>

                    <div class="row">
                        <div class="form-group col-md-12">
                            <div class="col-md-7">
                                <label class="control-label" for="service">Service</label>
                                <form:select path="service" class="form-control input-sm">
                                    <form:options items="${servicesList}" itemValue="id" itemLabel="name"/>
                                </form:select>

                                <div class="has-error">
                                        <%--<form:errors path="userProfiles" class="help-inline"/>--%>
                                    <form:errors path="service" class="help-inline" cssStyle="color: red"/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group col-md-12">
                            <div class="col-md-7">
                                <label class="control-label" for="name">Name</label>
                                <form:input type="text" path="name" id="name" class="form-control input-sm"/>
                                <div class="has-error">
                                    <form:errors path="name" class="help-inline"/>
                                </div>

                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-actions floatRight">
                            <c:choose>
                                <c:when test="${edit}">
                                    <input type="submit" value="Update" class="btn btn-primary btn-sm"/> or <a
                                        href="<c:url value='/categorieslist' />">Cancel</a>
                                </c:when>
                                <c:otherwise>
                                    <input type="submit" value="Add" class="btn btn-primary btn-sm"/> or <a
                                        href="<c:url value='/categorieslist' />">Cancel</a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </form:form>

            </div>
        </div>
    </jsp:body>
</page:template>
