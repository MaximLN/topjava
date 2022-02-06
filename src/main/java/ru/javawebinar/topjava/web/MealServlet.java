package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.SimulateDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        switch (action) {
            case "/delete":
                deleteMeal(request, response);
                break;
            case "/update":
                updateMeal(request, response);
                break;
            case "/add":
                addMeal(request, response);
                break;
            default:
                listMeal(request, response);
                break;
        }
    }

    private void listMeal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to listMeal");
        request.setAttribute("listMealTo", MealsUtil.filteredByStreams(SimulateDAO.getMeals(), LocalTime.of(7, 0), LocalTime.of(12, 0), SimulateDAO.getCaloriesPerDay()));
        RequestDispatcher dispatcher = request.getRequestDispatcher("meals.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteMeal(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.debug("redirect to deleteMeal");
        int id = Integer.parseInt(request.getParameter("id"));
        SimulateDAO.deleteMeals(id);
        response.sendRedirect("meals");
    }

    private void updateMeal(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("update.jsp");
        dispatcher.forward(request, response);
        int id = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("meal", SimulateDAO.getMeals().get(id));
        log.debug("redirect to updateMeal. Set: " + SimulateDAO.getMeals().get(id).getDateTime());
        SimulateDAO.updateMeals(id, new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"), Integer.parseInt(request.getParameter("calories"))));
    }

    private void addMeal(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("add.jsp");
        dispatcher.forward(request, response);
        log.debug("redirect to addMeal");
        SimulateDAO.addMeals(new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"), Integer.parseInt(request.getParameter("calories"))));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}


