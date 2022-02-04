<%@ page import="ru.javawebinar.topjava.web.MealServlet" %>
<%@ page import="ru.javawebinar.topjava.util.MealsUtil" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="ru.javawebinar.topjava.util.TempMealList" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<i><% List <MealTo> list = MealsUtil.filteredByStreams(TempMealList.getMeals(), LocalTime.of(7, 0), LocalTime.of(12, 0),TempMealList.getCaloriesPerDay());
String color;
for (MealTo meal : list) {
    if (meal.toString().contains("excess=true")) {
    color = "red";}
    else color = "green";
    %>
        <%= "<font color=\""+color+"\">"+meal+"</font>" %>
    <% } %>
</i>
</body>
</html>