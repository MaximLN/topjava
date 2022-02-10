<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Users</h2>

<br><br>
<table border="1" cellpadding="8" cellspacing="0">
    <thead>
    <tr>
        <th>Name</th>
        <th>Email</th>
        <th>Role</th>
        <th>Enabled</th>
        <th>Date registered</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="varusers" items="${users}">
    <td>${varusers.name}</td>
    <td>${varusers.email}</td>
    <td>${varusers.roles}</td>
    <td>${varusers.enabled}</td>
    <td>${varusers.registered}</td>
    </c:forEach>
    </tbody>
</table>

</body>
</html>