package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.repository.Dao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.repository.DaoMealsInMemory;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    Dao mealsInMemory = new DaoMealsInMemory();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        switch (action) {
            case "/meals-del":
                delete(request, response);
                break;
            case "/meals-upd":
                update(request, response);
                break;
            case "/meals-add":
                add(request, response);
                break;
            default:
                show(request, response);
        }
    }

    private void show(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int caloriesPerDay = 2000;
        List<MealTo> listMealTo = MealsUtil.filteredByStreams(mealsInMemory.getList(), LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
        log.debug("toJspMealList size: {}", listMealTo.size());
        request.setAttribute("toJspMealList", listMealTo);
        RequestDispatcher dispatcher = request.getRequestDispatcher("meals.jsp");
        dispatcher.forward(request, response);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        mealsInMemory.delete(id);
        log.debug("delete meal = {}", id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("meals");
        dispatcher.forward(request, response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("meals-upd.jsp");
        int id = Integer.parseInt(request.getParameter("id"));
        log.debug("get update meal id: {}", id);
        request.setAttribute("meal", mealsInMemory.getList().get(mealsInMemory.searchIndexInListById(id)));
        dispatcher.forward(request, response);
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("meals-add.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getServletPath();
        switch (action) {
            case "/meals-upd":
                int id = Integer.parseInt(request.getParameter("id"));
                mealsInMemory.update(new Meal(id, LocalDateTime.parse(request.getParameter("dateTime")),
                        request.getParameter("description"), Integer.parseInt(request.getParameter("calories"))));
                log.debug("doPost update meal id: {}", id);
                break;
            case "/meals-add":
                mealsInMemory.add(new Meal(mealsInMemory.createNewId(), LocalDateTime.parse(request.getParameter("dateTime")),
                        request.getParameter("description"), Integer.parseInt(request.getParameter("calories"))));
                log.debug("doPost add new meal");
                break;
        }
        response.sendRedirect("meals");
    }
}



