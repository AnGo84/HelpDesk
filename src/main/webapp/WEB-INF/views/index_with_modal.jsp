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
                    <a class="btn btn-info" href="<c:url value='#' />" role="button" data-placement="right"
                       title="create new Task">
                        <span class="glyphicon glyphicon-plus"></span>
                        Add New Task</a>
                </div>

                <!-- Table block -->
                <div>
                    <!-- table-responsive -->

                    <table id="taskstable"
                           class="table table-striped table-bordered dataTable table-hover table-condensed"
                           cellspacing="0" width="100%">

                        <thead>
                        <tr>
                            <th>
                                Id
                            </th>
                            <th>
                                Номер
                            </th>
                            <th>
                                Сервис
                            </th>
                            <th>
                                Категория
                            </th>
                            <th>
                                Тема
                            </th>
                            <th>
                                Дата от
                            </th>
                            <th>
                                Состояние
                            </th>
                            <th>
                                Инициатор
                            </th>
                            <th>
                                Ответственный
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>
                                1
                            </td>
                            <td>
                                <p>
                                    <a href="task.html">В2-1</a>
                                </p>
                            </td>
                            <td>
                                ОДБ
                            </td>
                            <td>
                                Репозитарий
                            </td>
                            <td>
                                Ошибка в файле 36
                            </td>
                            <td>
                                01.01.2015
                            </td>
                            <td>
                                Открыта
                            </td>
                            <td>
                                Иванов И.И.
                            </td>
                            <td>
                                Креатор
                            </td>
                        </tr>
                        <tr>
                            <td>
                                2
                            </td>
                            <td>
                                <p>
                                    <a href="task">Тел-1</a>
                                </p>
                            </td>
                            <td>
                                Телефония
                            </td>
                            <td>
                                Аппаратура
                            </td>
                            <td>
                                Не работает кнопка ответить
                            </td>
                            <td>
                                02.02.2015
                            </td>
                            <td>
                                Выполнена
                            </td>
                            <td>
                                Петров П.П.
                            </td>
                            <td>
                                Телефонист1
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <!-- End Table block -->
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

        <%--Modal--%>
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
        <%--Table--%>
        <script>
            $(document).ready(function () {
                // DataTable
                $('#taskstable').DataTable(
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

<!-- Modal -->
<div id="addTask" class="modal fade" role="dialog" data-backdrop="static">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Add New Task</h4>
            </div>
            <div class="modal-body">
                <!-- основное содержимое (тело) модального окна -->
                <%--<div class="container-fluid">--%>
                <div class="container">
                    <!-- Контейнер, в котором можно создавать классы системы сеток -->
                    <div class="row">
                        <form class="form-horizontal" name="form_add_task">
                            <!-- Categories -->
                            <div class="form-group">
                                <label for="categories" class="col-sm-3 control-label"> Категория </label>
                                <div class="col-sm-9">
                                    <select id="categories" class="selectpicker">
                                        <option value="">Выберите категорию</option>
                                        <option value="B2">Б2</option>
                                        <option value="Phone">Телефония</option>
                                        <option value="Comp">Компьютер</option>
                                    </select>
                                </div>
                            </div>
                            <!-- Subcategories -->
                            <div class="form-group">
                                <label for="subcategories" class="col-sm-3 control-label"> Подкатегория </label>
                                <div class="col-sm-9">
                                    <select id="subcategories" class="selectpicker">
                                        <option value="">Выберите подкатегорию</option>
                                        <option value="B2_dfl" class="B2">Депозиты ФЛ</option>
                                        <option value="B2_doc" class="B2">Документы дня</option>
                                        <option value="B2_credit" class="B2">Кредиты</option>
                                        <option value="Ph_inner" class="Phone">Внутренний</option>
                                        <option value="Ph_outer" class="Phone">Муждугородный</option>
                                        <option value="Comp_word" class="Comp">Word</option>
                                        <option value="Comp_mouse" class="Comp">Мышка</option>
                                    </select>
                                </div>
                            </div>

                            <!-- Theme -->
                            <div class="form-group">
                                <label for="theme" class="col-sm-3 control-label"> Название </label>
                                <div class="col-sm-9">
                                    <input type="text" id="theme" class="form-control"
                                           placeholder="Введите название заявки">
                                </div>
                            </div>
                            <!-- Description -->
                            <div class="form-group">
                                <label for="description" class="col-sm-3 control-label"> Описание </label>
                                <div class="col-sm-9">
																	<textarea id="description"
                                                                              class="form-control vresize">
																	</textarea>
                                </div>
                            </div>
                            <!-- Priority -->
                            <div class="form-group">
                                <label for="priority" class="col-sm-3 control-label"> Приоритет </label>
                                <div class="col-sm-9">
                                    <select id="priority" class="selectpicker">
                                        <option value="">Выберите приоритет</option>
                                        <option value="Low" class="priority">Низкий</option>
                                        <option value="Middle" class="priority">Средний</option>
                                        <option value="Hiht" class="priority">Высокий</option>
                                        <option value="Urgent" class="priority">Срочно</option>
                                    </select>
                                </div>
                            </div>
                            <!-- Task type -->
                            <div class="form-group">
                                <label for="type" class="col-sm-3 control-label"> Приоритет </label>
                                <div class="col-sm-9">
                                    <select id="type" class="selectpicker">
                                        <option value="">Тип заявки</option>
                                        <option value="New" class="priority">Новое требование</option>
                                        <option value="Consultation" class="priority">Консультация</option>
                                        <option value="Error" class="priority">Ошибка</option>
                                        <option value="Other" class="priority">Другое</option>
                                    </select>
                                </div>
                            </div>
                            <!-- ???????????? -->
                            <div class="form-group">
                                <div class="col-sm-offset-3 col-sm-9">
                                    <div class="checkbox">
                                        <label>
                                            <input type="checkbox"> Открыть после создания
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </form>

                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close
                    <span class="glyphicon glyphicons-remove"/>
                    </span>
                </button>
                <button type="button" class="btn btn-primary">Add
                    <span class="glyphicon glyphicons-ok"/>
                    </span>
                </button>
            </div>
        </div>
    </div>
</div>
<!-- End Modal -->
