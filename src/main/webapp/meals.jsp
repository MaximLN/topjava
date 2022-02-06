<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>

<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<br>
<a href="add">Add meal</a>
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

    <c:forEach var="meal" items="${listMealTo}" varStatus="commentLoop">
        <tr style="color:${meal.excess ? 'red' : 'green'}">
            <td>
                <fmt:parseDate value="${meal.dateTime}"
                               type="both" var="parsedDatetime" pattern="yyyy-MM-dd'T'HH:mm"/>
                <fmt:formatDate value="${parsedDatetime}" pattern="yyyy-MM-dd' 'HH:mm"/>
            </td>
            <td>
                <c:out value="${meal.description}"/>
            </td>
            <td>
                <c:out value="${meal.calories}"/>
            </td>
            <td>
                <a href="update?id=<c:out value="${commentLoop.index}"/>">Update</a>
            </td>
            <td>
                <a href="delete?id=<c:out value="${commentLoop.index}"/>">Delete</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>

</table>
</body>

</html>