<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
    <a href="meals?action=create">Add Meal</a>

    <br><br>
    <form method="get" name="filterform">
        <table border="0" cellpadding="8" cellspacing="0">
            <thead>
            <tr>
                <th>От даты (включая)</th>
                <th>До даты (включая)</th>
                <th>От времени (включая)</th>
                <th>До времени (исключая)</th>
            </tr>
            </thead>
            <tr>
                <td><label>
                    <input type="date" name="fromDate"/>
                </label></td>
                <td><label>
                    <input type="date" name="beforeDate"/>
                </label></td>
                <td><label>
                    <input type="time" name="fromTime"/>
                </label></td>
                <td><label>
                    <input type="time" name="beforeTime"/>
                </label></td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td>
                    <input type="button" value="Отфильтровать" onclick=
                            "location.href='meals?action=filter&fromDate='+escape(document.forms['filterform'].elements['fromDate'].value)
+'&beforeDate='+escape(document.forms['filterform'].elements['beforeDate'].value)
+'&fromTime='+escape(document.forms['filterform'].elements['fromTime'].value)
+'&beforeTime='+escape(document.forms['filterform'].elements['beforeTime'].value)">
                </td>
            </tr>
        </table>
    </form>

    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>