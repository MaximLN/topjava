<%@ page import="ru.javawebinar.topjava.web.MealServlet" %>
<%@ page import="ru.javawebinar.topjava.util.MealsUtil" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="ru.javawebinar.topjava.util.TempMealList" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<i>Meals <%= MealsUtil.filteredByStreams(TempMealList.getMeals(), LocalTime.of(7, 0), LocalTime.of(12, 0),TempMealList.getCaloriesPerDay())%></i>
</body>
</html>