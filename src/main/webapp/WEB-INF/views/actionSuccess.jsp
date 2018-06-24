<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:template>

    <jsp:attribute name="title">Confirmation Page</jsp:attribute>

    <jsp:body>
        <c:set var="linkto" value="${linkto}"/>
        <c:set var="linktotext" value="${linktotext}"/>

        <div class="generic-container">
            <div class="container">

                <div class="alert alert-success lead">
                        ${success}
                </div>

                <div class="well">
                    Go to <a href="<c:url value="${linkto}"/>">${linktotext}</a>
                </div>

                <%--<span class="well floatRight">--%>
			<%--Go to <a href="<c:url value="${linkto}"/>">${linktotext}</a>--%>
		<%--</span>--%>

            </div>
        </div>

    </jsp:body>
</page:template>
