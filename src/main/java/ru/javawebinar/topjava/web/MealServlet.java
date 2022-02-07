package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.SimulateDAO.DAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    DAO dao = new DAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        log.debug("request.getServletPath()" + request.getServletPath());
        String action = request.getServletPath();
        switch (action) {
            case "/meals-del":
                deleteMeal(request, response);
                break;
            case "/meals-upd":
                updateMeal(request, response);
                break;
            case "/meals-add":
                addMeal(request, response);
                break;
            default:
                listMeal(request, response);
        }
    }

    private void listMeal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to listMeal");
        Map<Long, MealTo> toJspMealToMap = new Hashtable<>();

        List<MealTo> listMealTo = MealsUtil.filteredByStreams(new ArrayList<>(dao.getMeals().values()),
                LocalTime.of(7, 0), LocalTime.of(12, 0), new User(2000).getCaloriesPerDay());
        for (Map.Entry<Long, Meal> entry : dao.getMeals().entrySet()) {
            for (MealTo mealTo : listMealTo) {
                if (entry.getValue().equals(mealTo)) {
                    toJspMealToMap.put(entry.getKey(), mealTo);
                    log.debug("toJspMealMap.put: " + entry.getKey());
                }
            }
        }

        request.setAttribute("toJspMealToMap", toJspMealToMap);
        RequestDispatcher dispatcher = request.getRequestDispatcher("meals.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteMeal(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.debug("redirect to deleteMeal");
        Long id = Long.parseLong(request.getParameter("id"));
        dao.deleteMeals(id);
        response.sendRedirect("meals");
    }

    private void updateMeal(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("meals-upd.jsp");
        Long id = Long.parseLong(request.getParameter("id"));
        log.debug("redirect to updateMeal. Set: " + dao.getMeals().get(id).getDateTime());
        request.setAttribute("meal", dao.getMeals().get(id));
        dispatcher.forward(request, response);
        dao.updateMeals(id, new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"), Integer.parseInt(request.getParameter("calories"))));
    }

    private void addMeal(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("meals-add.jsp");
        dispatcher.forward(request, response);
        dao.addMeals(System.currentTimeMillis(), new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"), Integer.parseInt(request.getParameter("calories"))));
        log.debug("add new meal");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}


