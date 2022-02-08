<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Add Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Add</h2>

<form method="post">

    <label for="localdate">DateTime: </label>
    <input type="datetime-local" id="localdate" name="dateTime"/><br/>

    <label>Description:
        <input type="text" name="description" value="Описание"><br/>
    </label>

    <label>Calories:
        <input type="text" name="calories" value="0"><br/>
    </label>
    <button type="submit" id="SubmitBtn">Submit</button>
</form>
<a href="meals">
    <button id="CloseBtn">Close</button>
</a>

</body>
</html>