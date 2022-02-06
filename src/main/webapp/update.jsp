<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Update</h2>

<form method="post">

    <label for="localdate">DateTime: </label>
    <input type="datetime-local" id="localdate" name="dateTime"/><br/>

    <label>Description:
        <input type="text" name="description"><br/>
    </label>

    <label>Calories:
        <input type="text" name="calories"><br/>
    </label>
    <button type="submit">Submit</button>
</form>

</body>
</html>