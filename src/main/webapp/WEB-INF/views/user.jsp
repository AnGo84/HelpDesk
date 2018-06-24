<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:template>

    <jsp:attribute name="title">User Form</jsp:attribute>

    <jsp:body>
        <div class="generic-container">
            <div class="container">
                <div class="well lead">User Registration Form</div>
                <form:form method="POST" modelAttribute="user" class="form-horizontal">
                    <form:input type="hidden" path="id" id="id"/>

                    <div class="row">
                        <div class="form-group col-md-12">
                                <%--<label class="col-md-1 col-md-offset-1 control-label" for="login">Login</label>--%>

                            <div class="col-md-7">
                                <label class="control-label" for="login">Login</label>
                                <c:choose>
                                    <c:when test="${edit}">
                                        <form:input type="text" path="login" id="login" class="form-control input-sm"
                                                    disabled="true"/>
                                    </c:when>
                                    <c:otherwise>
                                        <form:input type="text" path="login" id="login" class="form-control input-sm"/>
                                        <div class="has-error">
                                            <form:errors path="login" class="help-inline"/>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>


                    <div class="row">
                        <div class="form-group col-md-12">
                                <%--<label class="col-md-3 control-label" for="lastName">Last Name</label>--%>
                            <div class="col-md-7">
                                <label class="control-label" for="lastName">Last Name</label>
                                <form:input type="text" path="lastName" id="lastName" class="form-control input-sm"/>
                                <div class="has-error">
                                    <form:errors path="lastName" class="help-inline" cssStyle="color: red"/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group col-md-12">
                            <div class="col-md-7">
                                <label class="control-label" for="firstName">First Name</label>
                                <form:input type="text" path="firstName" id="firstName" class="form-control input-sm"/>
                                <div class="has-error">
                                    <form:errors path="firstName" class="help-inline" cssStyle="color: red"/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group col-md-12">
                            <div class="col-md-7">
                                <label class="control-label" for="middleName">Middle Name</label>
                                <form:input type="text" path="middleName" id="middleName"
                                            class="form-control input-sm"/>
                                <div class="has-error">
                                    <form:errors path="middleName" class="help-inline" cssStyle="color: red"/>
                                </div>
                            </div>
                        </div>
                    </div>


                    <div class="row">
                        <div class="form-group col-md-12">
                            <div class="col-md-7">
                                <label class="control-label" for="password">Password</label>
                                <form:input type="password" path="password" id="password"
                                            class="form-control input-sm"/>
                                <div class="has-error">
                                    <form:errors path="password" class="help-inline" cssStyle="color: red"/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group col-md-12">
                            <div class="col-md-7">
                                <label class="control-label" for="email">Email</label>
                                <form:input type="email" path="email" id="email" class="form-control input-sm"/>
                                <div class="has-error">
                                    <form:errors path="email" class="help-inline" cssStyle="color: red"/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group col-md-12">
                            <div class="col-md-7">
                                <label class="control-label" for="phone">Phone</label>
                                <form:input type="text" path="phone" id="phone" class="form-control input-sm"/>
                                <div class="has-error">
                                    <form:errors path="phone" class="help-inline" cssStyle="color: red"/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group col-md-12">
                            <div class="col-md-7">
                                <label class="control-label" for="userType">Type</label>
                                    <%--<form:select path="userProfiles" items="${roles}" multiple="true" itemValue="id" itemLabel="name" class="form-control input-sm" />--%>
                                    <%--<form:select path="userType" items="${roles}" itemValue="id" itemLabel="name"--%>
                                    <%--class="form-control input-sm"/>--%>
                                <form:select path="userType" class="form-control input-sm">
                                    <form:options items="${roles}" itemValue="id" itemLabel="name"/>
                                </form:select>

                                <div class="has-error">
                                    <form:errors path="userType" class="help-inline" cssStyle="color: red"/>
                                </div>
                            </div>
                        </div>
                    </div>


                    <div class="row">
                        <div class="form-group col-md-12">
                            <div class="col-md-7">
                                <label class="control-lable" for="groups">Groups</label>
                                <form:select path="groups" items="${groupsList}" multiple="true" itemValue="id"
                                             itemLabel="name" class="form-control input-sm"/>
                                <div class="has-error">
                                    <form:errors path="groups" class="help-inline"/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group col-md-12">
                                <div class="col-md-1">
                                    <label class="control-label text-left" for="active">Active</label>
                                    <%--<form:checkbox path="active" id="active" class="form-control input-sm"/>--%>
                                    <form:checkbox path="active" id="active" class="form-control checkbox"/>
                                </div>

                        </div>
                    </div>

                    <div class="row">
                        <div class="form-actions floatRight">
                            <c:choose>
                                <c:when test="${edit}">
                                    <input type="submit" value="Update" class="btn btn-primary btn-sm"/> or <a
                                        href="<c:url value='/userslist' />">Cancel</a>
                                </c:when>
                                <c:otherwise>
                                    <input type="submit" value="Register" class="btn btn-primary btn-sm"/> or <a
                                        href="<c:url value='/userslist' />">Cancel</a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </form:form>

            </div>
        </div>
    </jsp:body>
</page:template>
