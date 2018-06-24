<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:template>

    <jsp:attribute name="title">Ticket</jsp:attribute>

    <jsp:body>
        <!-- HEAD -->
        <div class="generic-container">
            <div class="container">
                <!-- INFO Panel-->
                <c:url var="findServiceCategoriesURL" value="/serviceCategories"/>

                <form:form method="POST" modelAttribute="ticket" class="form-horizontal">
                    <form:input type="hidden" path="id" id="id"/>
                    <form:input type="hidden" path="number" id="number"/>
                    <div class="has-error">
                        <form:errors path="number" class="help-inline"/>
                    </div>
                    <form:input type="hidden" path="date" id="date"/>
                    <div class="has-error">
                        <form:errors path="date" class="help-inline"/>
                    </div>
                    <form:input type="hidden" path="user" id="user"/>
                    <div class="has-error">
                        <form:errors path="user" class="help-inline"/>
                    </div>

                    <div class="panel-heading">
                        <h5 class="panel-title">
                            <div class="row">
                                <div class="col-sm-2">
                                    <strong> ID: </strong> ${ticket.id}
                                </div>
                                <div class="col-sm-3">
                                    <strong> Number: </strong> ${ticket.number}
                                </div>
                                <div class="col-sm-3">
                                        <%--${ticket.date}--%>
                                    <strong> Date: </strong> <fmt:formatDate value="${ticket.date}" type="date"
                                                                             pattern="dd MMMM yyyy"/>
                                </div>
                                <div class="col-sm-4">
                                    <strong> User: </strong> ${ticket.user.login}
                                </div>
                            </div>
                        </h5>
                    </div>
                    <div class="panel-body">

                        <fieldset>
                            <div class="multiple">
                                <div class="row">
                                    <div class="form-group col-md-12">
                                        <div class="col-md-6">
                                            <label class="control-lable" for="service">Service</label>
                                            <form:select id="service" path="service" class="form-control input-sm"
                                                         style="text-align: left">
                                                <form:options items="${servicesList}" itemValue="id" itemLabel="name"/>
                                            </form:select>

                                        </div>

                                        <div class="col-md-6">
                                            <label class="control-lable" for="category">Category</label>
                                            <form:select id="category" path="category" class="form-control input-sm"
                                                         style="text-align: left">
                                                <form:option value="">Category</form:option>
                                            </form:select>
                                            <div class="has-error">
                                                <form:errors path="category" class="help-inline"/>
                                            </div>
                                            <%--<div id="output"></div>--%>

                                        </div>

                                    </div>
                                </div>
                            </div>
                        </fieldset>

                        <div class="row">

                            <!-- Theme -->
                            <div class="form-group">
                                <div class="col-md-12">
                                    <label class="control-label" for="theme">Theme</label>
                                    <form:input type="text" path="theme" id="theme" class="form-control input-sm"/>
                                    <div class="has-error">
                                        <form:errors path="theme" class="help-inline"/>
                                    </div>
                                </div>
                            </div>
                            <!-- Description -->
                            <div class="form-group">
                                <div class="col-md-12">
                                    <label class="control-label" for="description">Description</label>
                                    <form:textarea type="text" path="description" id="description"
                                                   class="form-control input-sm"/>
                                    <div class="has-error">
                                        <form:errors path="description" class="help-inline"/>
                                    </div>
                                </div>
                            </div>
                            <!-- Solution -->
                            <div class="form-group">
                                <div class="col-md-12">
                                    <label class="control-label" for="solution">Solution</label>
                                    <form:textarea type="text" path="solution" id="solution"
                                                   class="form-control input-sm"/>
                                    <div class="has-error">
                                        <form:errors path="solution" class="help-inline"/>
                                    </div>
                                </div>
                            </div>


                        </div>
                    </div>

                    <div class="panel-footer">
                        <div class="row">

                            <div class="col-sm-2">
                                <label class="control-lable" for="priority"><strong>Priority</strong></label>

                                <form:select path="priority" class="form-control input-sm">
                                    <form:options items="${prioritiesList}" itemValue="id" itemLabel="name"/>
                                </form:select>

                            </div>
                            <div class="col-sm-2">
                                <label class="control-label" for="ticketType"><strong>Type</strong></label>
                                <form:select path="ticketType" class="form-control input-sm">
                                    <form:options items="${ticketTypesList}" itemValue="id" itemLabel="name"/>
                                </form:select>
                            </div>
                            <div class="col-sm-2">
                                <label class="control-label" for="ticketState"><strong>State</strong></label>
                                <form:select path="ticketState" class="form-control input-sm">
                                    <form:options items="${ticketStatesList}" itemValue="id" itemLabel="name"/>
                                </form:select>
                            </div>

                            <div class="col-sm-4">
                                <label class="control-label" for="performer"><strong> Performer: </strong></label>
                                <form:select path="performer" class="form-control input-sm">
                                    <form:options items="${usersList}" itemValue="id" itemLabel="Login"/>
                                </form:select>
                                <div class="has-error">
                                    <form:errors path="performer" class="help-inline"/>
                                </div>
                            </div>

                            <div class="col-sm-2">
                                    <%--<button type="submit" class="btn btn-default" style="width:150px" data-toggle="modal"--%>
                                    <%--data-placement="right" title="confirm changes">Confirm--%>
                                    <%--<span class="glyphicon glyphicon-ok"/>--%>
                                    <%--</span>--%>
                                    <%--</button>--%>

                                    <%--<input type="submit" value="Confirm" class="btn btn-primary btn-sm"/>--%>
                                <div class="form-actions floatRight">
                                    <input type="submit" value="Confirm" class="btn btn-primary btn-sm"
                                           style="width:150px"/>
                                </div>
                            </div>

                        </div>
                    </div>
                </form:form>


                <!-- New Comment -->
                <div class="panel panel-info">
                    <!-- HEAD -->
                    <div class="panel-heading">
                        <h5 class="panel-title">
                            <div class="row">
                                <div class="col-sm-3">
                                    <strong> New comment </strong>
                                </div>
                            </div>
                        </h5>
                    </div>
                    <div class="panel-body">

                        <!-- Контейнер, в котором можно создавать классы системы сеток -->
                        <div class="row">
                            <form:form class="form-horizontal" name="sentMessage" id="contactForm" action="${send}"
                                       method="post" modelAttribute="emailModel" novalidate="true">
                                <div class="col-sm-10">
                            <textarea class="form-control vresize"
                                      id="message" name="message"
                                      required
                                      data-validation-required-message="Please enter your message"
                                      maxlength="999">
                            </textarea>
                                </div>
                                <div class="col-sm-2">
                                    <button type="submit" class="btn btn-default" style="width:150px"
                                            data-toggle="modal"
                                            data-placement="right" title="Add new comment">Add
                                        <span class="glyphicon glyphicon-upload"/>
                                        </span>
                                    </button>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>
                <!-- End New Comment -->

                <!-- Table block -->

                <!-- table-responsive -->

                <table class="table table-bordered">
                    <caption>
                        <b>Comments</b>
                    </caption>
                    <thead>
                    <tr>
                        <th>Text</th>
                        <th>Date</th>
                        <th>Author</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td><textarea id="description1" class="form-control vresize"
                                      readonly="readonly">Комментарии 1</textarea></td>
                        <td>01.01.2016 12:20:56</td>
                        <td>Петров П.П.</td>
                    </tr>
                    <tr>
                        <td><textarea id="description2" class="form-control vresize"
                                      readonly="readonly">Комментарии 2</textarea></td>
                        <td>02.01.2016 12:20:56</td>
                        <td>Сидоров С.С.</td>
                    </tr>
                    </tbody>
                </table>

                <!-- END Table block -->


            </div>
        </div>



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

        <%--TEST with output--%>
        <%--<script type="text/javascript">--%>
            <%--$(document).ready(function () {--%>
                <%--$("#category").change(onSelectChange);--%>
            <%--});--%>

            <%--function onSelectChange() {--%>
                <%--var selected = $("#category option:selected");--%>
                <%--var output = "";--%>
                <%--if (selected.val() != 0) {--%>
                    <%--output = "You selected Category " + selected.text();--%>
                <%--}--%>
                <%--$("#output").html(output);--%>
            <%--}--%>

        <%--</script>--%>

        <!-- FILES Panel -->
        <%--<c:url value="/uploadFile" var="fileUploadControllerURL"/>--%>

        <%--<div class="panel panel-default">--%>
        <%--<!-- HEAD -->--%>
        <%--<div class="panel-heading">--%>
        <%--<h5 class="panel-title">--%>
        <%--Прикреплённые файлы--%>
        <%--</h5>--%>
        <%--</div>--%>
        <%--<div class="panel-body">--%>

        <%--<!-- Контейнер, в котором можно создавать классы системы сеток -->--%>
        <%--<div class="row">--%>
        <%--<table class="table table-bordered table-condensed">--%>
        <%--<tbody>--%>
        <%--<tr>--%>
        <%--<td>2016.10.07_sel_01.xls</td>--%>
        <%--<td>--%>
        <%--<button type="button" class="btn btn-default" style="width:150px" data-toggle="modal"--%>
        <%--data-placement="right" title="Delete selected file">Delete--%>
        <%--</button>--%>
        <%--</td>--%>

        <%--</tr>--%>
        <%--<tr>--%>
        <%--<td>work_ora_12221.trc</td>--%>
        <%--<td>--%>
        <%--<button type="button" class="btn btn-default" style="width:150px" data-toggle="modal"--%>
        <%--data-placement="right" title="Delete selected file">Delete--%>
        <%--</button>--%>
        <%--</td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
        <%--<td>ReadMe.txt</td>--%>
        <%--<td>--%>
        <%--<button type="button" class="btn btn-default" style="width:150px" data-toggle="modal"--%>
        <%--data-placement="right" title="Delete selected file">Delete--%>
        <%--</button>--%>
        <%--</td>--%>
        <%--</tr>--%>
        <%--</tbody>--%>
        <%--</table>--%>
        <%--</div>--%>
        <%--<!-- END Контейнер, в котором можно создавать классы системы сеток -->--%>
        <%--</div>--%>

        <%--<div class="col-lg-12">--%>
        <%--<p>Пример загрузки файла с помощью Spring MVC </p>--%>
        <%--<form action="${fileUploadControllerURL}" method="post" enctype="multipart/form-data">--%>
        <%--<table>--%>
        <%--<tr>--%>
        <%--<td>--%>
        <%--<b>File:</b>--%>
        <%--</td>--%>
        <%--<td>--%>
        <%--<input type="file" name="file">--%>
        <%--</td>--%>
        <%--<td>--%>
        <%--<input type="submit" value="загрузить файл">--%>
        <%--</td>--%>
        <%--</tr>--%>
        <%--</table>--%>
        <%--</form>--%>

        <%--<br/>--%>

        <%--<c:url value="/excel" var="excelController"/>--%>
        <%--<c:url value="/pdf" var="pdfController"/>--%>
        <%--<a href="${excelController}">Excel</a>--%>
        <%--<br/>--%>
        <%--<a href="${pdfController}">PDF</a>--%>

        <%--</div>--%>

        <%--<div class="panel-footer">--%>
        <%--<div class="row">--%>

        <%--<div class="col-sm-10">--%>
        <%--<input type="file" class="filestyle" data-buttonBefore="true" data-size="sm"--%>
        <%--data-buttonText="Find file">--%>
        <%--</div>--%>
        <%--<div class="col-sm-2">--%>
        <%--<button type="button" class="btn btn-default" style="width:150px" data-toggle="modal"--%>
        <%--data-placement="right" title="Add choosed file">Add--%>
        <%--<span class="glyphicon glyphicon-upload"/>--%>
        <%--</span>--%>
        <%--</button>--%>
        <%--</div>--%>

        <%--</div>--%>

        <%--</div>--%>
        <%--</div>--%>


        <%--
        </div>
        </div>
        --%>

        <!-- Go to TOP -->
        <a id="back-to-top" href="#" class="btn btn-default btn-sm back-to-top" role="button" title="Back to Top"
           data-toggle="tooltip" data-placement="top">
            <span class="glyphicon glyphicon-chevron-up"/>
        </a>
        <!--</div></div> -->


        <!-- FOOTER
        <footer class="container-fluid text-center">
        <img src="images/logo_full.jpg" class="img-rounded" alt="Logo of bank">
        <p>Link to Bank sait <a href="http://crystalbank.com.ua/">crystalbank.com.ua/</a>
        </p>
        </footer>
        -->
        <script>
            $(function () {
                // Очистим форму чтобы при перезагрузке страницы
                // данные формы вернулись в исходное значение
                $("form").trigger("reset");
                // активируем плагин jQuery Chained Selects
                $("#subcategories").chained("#categories");
                //при изменении значения 1го списка:
                $("#categories").change(function () {
                    $("#subcategories").selectpicker('refresh');
                });
            });
        </script>
    </jsp:body>
</page:template>