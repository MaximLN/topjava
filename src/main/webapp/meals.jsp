<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>

<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<br>
<a href="meals-add">Add meal</a>
<br>
<table cellspacing="2" border="1" cellpadding="5">

    <thead>
    <tr>
        <th>DateTime</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Update</th>
        <th>Delete</th>
    </tr>
    </thead>
    <tbody>

    <c:forEach var="entry" items="${toJspMealToMap}">
        <tr style="color:${entry.value.excess ? 'red' : 'green'}">
            <td>
                <fmt:parseDate value="${entry.value.dateTime}"
                               type="both" var="parsedDatetime" pattern="yyyy-MM-dd'T'HH:mm"/>
                <fmt:formatDate value="${parsedDatetime}" pattern="yyyy-MM-dd' 'HH:mm"/>
            </td>

            <td>
                <c:out value="${entry.value.description}"/>
            </td>
            <td>
                <c:out value="${entry.value.calories}"/>
            </td>
            <td>
                <a href="meals-upd?id=<c:out value="${entry.key}"/>">Update</a>
            </td>
            <td>
                <a href="meals-del?id=<c:out value="${entry.key}"/>">Delete</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>

</table>
</body>

</html>