<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:template>

    <jsp:attribute name="title">Add ticket</jsp:attribute>

    <jsp:body>
        <div class="generic-container">
            <div class="container">
                <div class="well lead">Ticket Registration Form</div>
                <form:form method="POST" modelAttribute="ticket" class="form-horizontal">
                    <form:input type="hidden" path="id" id="id"/>
                    <form:input type="hidden" path="number" id="number"/>

                    <div class="row">
                        <div class="form-group col-md-12">
                            <div class="col-md-7">
                                <label class="control-lable" for="priority">Priority</label>

                                <form:select path="priority" class="form-control input-sm">
                                    <form:options items="${prioritiesList}" itemValue="id" itemLabel="name"/>
                                </form:select>

                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group col-md-12">
                            <div class="col-md-7">
                                <label class="control-lable" for="ticketType">Type</label>
                                <form:select path="ticketType" class="form-control input-sm">
                                    <form:options items="${ticketTypesList}" itemValue="id" itemLabel="name"/>
                                </form:select>
                            </div>
                        </div>
                    </div>


                    <fieldset>
                        <div class="multiple">


                            <div class="row">
                                <div class="form-group col-md-12">
                                    <div class="col-md-7">
                                        <label class="control-lable" for="service">Service</label>

                                        <form:select id="service" path="service" class="form-control input-sm">
                                            <form:options items="${servicesList}" itemValue="id" itemLabel="name"/>
                                        </form:select>

                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="form-group col-md-12">
                                    <div class="col-md-7">
                                        <label class="control-lable" for="category">Category</label>
                                            <%--
                                            <form:select path="category" class="form-control input-sm selectpicker">
                                                <form:options items="${categoriesList}" itemValue="id" itemLabel="name" class="${category.service.id}" />
                                            </form:select>
                                            --%>
                                            <%--<fieldset>--%>
                                            <%--
                                            <form:select id="usStates" path="usState">
                                            </form:select>
                                            --%>

                                        <form:select id="category" path="category" class="form-control input-sm">
                                            <form:option value="">Category</form:option>
                                        </form:select>
                                            <%--</fieldset>--%>

                                        <div id="output"></div>

                                    </div>
                                </div>
                            </div>

                        </div>
                    </fieldset>

                    <div class="row">
                        <div class="form-group col-md-12">
                            <div class="col-md-7">
                                <label class="control-label" for="theme">Theme</label>
                                <form:input type="text" path="theme" id="theme" class="form-control input-sm"/>
                                <div class="has-error">
                                    <form:errors path="theme" class="help-inline"/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group col-md-12">
                            <div class="col-md-7">
                                <label class="control-label" for="description">Description</label>
                                <form:textarea type="text" path="description" id="description"
                                               class="form-control input-sm"/>
                                <div class="has-error">
                                    <form:errors path="description" class="help-inline"/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <%--<div class="row">--%>
                    <%--<div class="form-group col-md-12">--%>
                    <%--<div class="col-md-1">--%>
                    <%--<label class="control-label text-left" for="right">Open after add</label>--%>
                    <%--<form:checkbox path="right" id="right" class="form-control input-sm"/>--%>
                    <%--</div>--%>
                    <%--</div>--%>
                    <%--</div>--%>

                    <%--<div class="row">
                        <div class="form-group col-md-12">
                            <div class="col-md-7">
                                <label class="control-lable" for="categories">Categories</label>
                                <form:select path="categories" items="${categoriesList}" multiple="true" itemValue="id"
                                             itemLabel="name" class="form-control input-sm selectpicker"/>
                                <div class="has-error">
                                    <form:errors path="categories" class="help-inline"/>
                                </div>
                            </div>
                        </div>
                    </div>--%>

                    <div class="row">
                        <div class="form-actions floatRight">
                            <input type="submit" value="Add" class="btn btn-primary btn-sm"/> or <a
                                href="<c:url value='/index' />">Cancel</a>
                        </div>
                    </div>
                </form:form>

            </div>
        </div>

        <c:url var="findServiceCategoriesURL" value="/serviceCategories"/>

        <script type="text/javascript">
            $(document).ready(function () {
                $('#service').change(
                    function () {
                        $.getJSON('${findServiceCategoriesURL}', {
                            serviceName: $(this).val(),
                            ajax: 'true'
                        }, function (data) {
                            var html = '<option value="">Category</option>';
                            var len = data.length;
                            //alert( len );
                            for (var i = 0; i < len; i++) {
                                html += '<option value="' + data[i].id + '">'
                                    + data[i].name + '</option>';
                            }
                            //html += '</option>';
                            //alert( html );
                            $('#category').html(html);
                        });
                    });
            });
        </script>


        <script type="text/javascript">
            $(document).ready(function () {
                $("#category").change(onSelectChange);
            });

            function onSelectChange() {
                var selected = $("#category option:selected");
                var output = "";
                if (selected.val() != 0) {
                    output = "You selected Category " + selected.text();
                }
                $("#output").html(output);
            }

        </script>

        <%--        <script type="text/javascript">
                    $(document).ready(function(){
                        $("#service").change(onSelectChange("#service"));
                    });

                    $(document).ready(function(){
                        $("#category").change(onSelectChange());
                    });
                    function onSelectChange(par1) {
                        //var selected = $("#service option:selected");

                        var selected = $(par1+" option:selected");
                        var output = "";
                        if(selected.val() != 0){
                            output = "You selected "+ par1 +" : " + selected.text();
                        }
                        $("#output").html(output);
                    }
                </script>--%>

    </jsp:body>
</page:template>
