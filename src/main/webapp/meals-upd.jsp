<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Update Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Update</h2>
<form method="post" >
    <label for="localdate">DateTime: </label>
    <input type="datetime-local" id="localdate" name="dateTime" value="${meal.dateTime}"/><br/>
    <label>Description:
        <input type="text" name="description" value="${meal.description}"><br/>
    </label>
    <label>Calories:
        <input type="text" name="calories" value="${meal.calories}"><br/>
    </label>
    <button type="submit" id="submitBtn">Submit </button>
</form>
<a href="meals">
<button id="CloseBtn">Close </button></a>
</body>
</html>