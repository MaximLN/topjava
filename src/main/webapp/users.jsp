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
        <th>Update</th>
        <th>Delete</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="users" items="${users}">
        <tr>
        <td>${users.name}</td>
        <td>${users.email}</td>
        <td>${users.roles}</td>
        <td>${users.enabled}</td>
        <td>${users.registered}</td>
        <td><a href="users?action=update&id=${users.id}">Update</a></td>
        <td><a href="users?action=delete&id=${users.id}">Delete</a></td>
            </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>