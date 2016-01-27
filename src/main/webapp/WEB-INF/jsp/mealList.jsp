<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<link rel="stylesheet" href="webjars/datatables/1.10.9/css/jquery.dataTables.min.css">
<link rel="stylesheet" href="webjars/datetimepicker/2.3.4/jquery.datetimepicker.css">
<body class="jumbotron">
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="container">
    <section>
        <%--http://stackoverflow.com/questions/10327390/how-should-i-get-root-folder-path-in-jsp-page--%>
        <h3><a href="${pageContext.request.contextPath}">Home</a></h3>
        <h3><fmt:message key="meals.title"/></h3>
        <div class="row">
            <form method="post" class="form-horizontal">
                <div class="col-md-5 col-md-offset-1">
                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="startDate">From Date:</label>
                        <input type="date" class="form-control" id="startDate" value="${startDate}">
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="startTime">From Time:</label>
                        <input type="time" class="form-control" id="startTime" value="${startTime}">
                    </div>
                </div>

                <div class="col-md-5 col-md-offset-1">
                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="endDate">To Date:</label>
                        <input type="date" class="form-control" id="endDate" value="${endDate}">
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="endTime">To Time:</label>
                        <input type="time" class="form-control" id="endTime" value="${endTime}">
                    </div>
                    <button class="btn btn-primary col-md-offset-8" type="submit">Filter</button>
                </div>

            </form>
        </div>
        <div class="row">
            <a class="btn btn-info" id="add">Add Meal</a>
        </div>
        <br>
        <div class="row">
            <table class="table table-condensed table-striped table-hover" id="datatable">
                <thead>
                <tr>
                    <th>Date</th>
                    <th>Description</th>
                    <th>Calories</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <c:forEach items="${mealList}" var="meal">
                    <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.UserMealWithExceed"/>
                    <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                        <td class="dateTime">
                                <%--<fmt:parseDate value="${meal.dateTime}" pattern="y-M-dd'T'H:m" var="parsedDate"/>--%>
                                <%--<fmt:formatDate value="${parsedDate}" pattern="yyyy.MM.dd HH:mm" />--%>
                            <%=TimeUtil.toString(meal.getDateTime())%>
                        </td>
                        <td class="description">${meal.description}</td>
                        <td class="calories">${meal.calories}</td>
                        <td><a class="btn btn-sm btn-primary edit" name="${meal.id}">Edit</a></td>
                        <td><a class="btn btn-sm btn-danger delete" name="${meal.id}">Delete</a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>

    </section>


</div>


<jsp:include page="fragments/footer.jsp"/>
<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title"><fmt:message key="meals.edit"/></h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" method="post" id="detailsForm">
                    <input type="text" hidden="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="dateTime" class="control-label col-xs-3">Date</label>

                        <div class="col-xs-9">
                            <input type="datetime-local" class="form-control" id="dateTime" name="dateTime">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="description" class="control-label col-xs-3">Description</label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="description" name="description" placeholder="description">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="calories" class="control-label col-xs-3">Calories</label>

                        <div class="col-xs-9">
                            <input type="number" class="form-control" id="calories" name="calories" placeholder="">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button type="submit" class="btn btn-primary">Save</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="webjars/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script type="text/javascript" src="webjars/datetimepicker/2.3.4/jquery.datetimepicker.js"></script>
<script type="text/javascript" src="webjars/datatables/1.10.9/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="webjars/noty/2.2.4/jquery.noty.packaged.min.js"></script>
<script type="text/javascript" src="resources/js/datatablesUtil.js"></script>
<script type="text/javascript">

    var ajaxUrl = '/ajax/profile/meals/';
    var oTable_datatable;
    var oTable_datatable_params;

    //            $(document).ready(function () {
    $(function () {
        oTable_datatable = $('#datatable');
        oTable_datatable_params = {
            "bPaginate": false,
            "bInfo": false,
            "aoColumns": [
                {
                    "mData": "dateTime"
                },
                {
                    "mData": "description"
                },
                {
                    "mData": "calories"
                },

                {
                    "sDefaultContent": "",
                    "bSortable": false
                },
                {
                    "sDefaultContent": "",
                    "bSortable": false
                }
            ],
            "aaSorting": [
                [
                    0,
                    "asc"
                ]
            ]
        };

        oTable_datatable.dataTable(oTable_datatable_params);
        makeEditable();
    });
</script>
</body>
</html>
