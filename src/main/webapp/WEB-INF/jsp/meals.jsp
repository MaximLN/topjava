<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/topjava.common.js" defer></script>
<script type="text/javascript" src="resources/js/topjava.meals.js" defer></script>
<link rel="stylesheet" type="text/css" href="resources/css/jquery.datetimepicker.css"/>
<script src="resources/js/jquery.js"></script>
<script src="resources/js/jquery.datetimepicker.js"></script>
<script src="resources/js/jquery.datetimepicker.full.min.js"></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron pt-4">
    <div class="container">
        <h3 class="text-center"><spring:message code="meal.title"/></h3>
        <%--https://getbootstrap.com/docs/4.0/components/card/--%>
        <div class="card border-dark">
            <div class="card-body pb-0">
                <form id="filter">
                    <div class="row">
                        <div class="col-3">
                            <label for="datetimepickerStartDate"><spring:message code="meal.startDate"/></label><br>
                            <input type="text" id="datetimepickerStartDate" name="startDate"/>
                            <script>
                                jQuery('#datetimepickerStartDate').datetimepicker({
                                    timepicker:false,
                                    defaultDate: new Date(),
                                    format: 'Y-m-d'
                                });
                            </script>
                        </div>
                        <div class="col-3">
                            <label for="datetimepickerEndDate"><spring:message code="meal.endDate"/></label><br>
                            <input type="text" id="datetimepickerEndDate" name="endDate"/>
                            <script>
                                jQuery('#datetimepickerEndDate').datetimepicker({
                                    timepicker:false,
                                    defaultDate: new Date(),
                                    format: 'Y-m-d'
                                });
                            </script>
                        </div>
                        <div class="offset-2 col-2">
                            <label for="datetimepickerStartTime"><spring:message code="meal.startTime"/></label><br>
                            <input type="text" id="datetimepickerStartTime" name="startTime"/>
                            <script>
                                jQuery('#datetimepickerStartTime').datetimepicker({
                                    datepicker:false,
                                    defaultDate: new Date(),
                                    format: 'H:i'
                                });
                            </script>
                        </div>
                        <div class="col-2">
                            <label for="datetimepickerEndTime"><spring:message code="meal.endTime"/></label><br>
                            <input type="text" id="datetimepickerEndTime" name="endTime"/>
                            <script>
                                jQuery('#datetimepickerEndTime').datetimepicker({
                                    datepicker:false,
                                    defaultDate: new Date(),
                                    format: 'H:i'
                                });
                            </script>
                        </div>
                    </div>
                </form>
            </div>
            <div class="card-footer text-right">
                <button class="btn btn-danger" onclick="clearFilter()">
                    <span class="fa fa-remove"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button class="btn btn-primary" onclick="ctx.updateTable()">
                    <span class="fa fa-filter"></span>
                    <spring:message code="meal.filter"/>
                </button>
            </div>
        </div>
        <br/>
        <button class="btn btn-primary" onclick="add()">
            <span class="fa fa-plus"></span>
            <spring:message code="common.add"/>
        </button>
        <table class="table table-striped" id="datatable">
            <thead>
            <tr>
                <th><spring:message code="meal.dateTime"/></th>
                <th><spring:message code="meal.description"/></th>
                <th><spring:message code="meal.calories"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="modalTitle"><spring:message code="meal.add"/></h4>
                <button type="button" class="close" data-dismiss="modal" onclick="closeNoty()">&times;</button>
            </div>
            <div class="modal-body">
                <form id="detailsForm">
                    <input type="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="datetimepicker" class="col-form-label"><spring:message code="meal.dateTime"/></label><br>
                        <input type="text" id="datetimepicker" name="dateTime"/>
                        <script>
                            jQuery('#datetimepicker').datetimepicker({
                                defaultDate: new Date(),
                                format: 'Y-m-d H:i'
                            });
                        </script>
                    </div>

                    <div class="form-group">
                        <label for="description" class="col-form-label"><spring:message
                                code="meal.description"/></label>
                        <input type="text" class="form-control" id="description" name="description"
                               placeholder="<spring:message code="meal.description"/>">
                    </div>

                    <div class="form-group">
                        <label for="calories" class="col-form-label"><spring:message code="meal.calories"/></label>
                        <input type="number" class="form-control" id="calories" name="calories" placeholder="1000">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="closeNoty()">
                    <span class="fa fa-close"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button type="button" class="btn btn-primary" onclick="save()">
                    <span class="fa fa-check"></span>
                    <spring:message code="common.save"/>
                </button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
<jsp:include page="fragments/addMessagesI18n.jsp"/>
</html>