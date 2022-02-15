package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.SpringMain;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
//    ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
//    private MealRepository repository;
    MealRestController mealRestController =  new MealRestController();

    @Override
    public void init() {
//        repository = appCtx.getBean(InMemoryMealRepository.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        System.out.println("doPost request.getParameter(id): " + request.getParameter("id"));
        String id = request.getParameter("id");
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                null,
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        log.info(meal.isNew() ? "Servlet Create {}" : "Servlet Update {}", meal);
        mealRestController.save(meal);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        log.info("repository size {}", mealRestController.getAll().size());
        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
                request.setAttribute("meal", new Meal(null, null, LocalDateTime.of(LocalDateTime.now().getYear(),
                        LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), LocalDateTime.now().getHour(),
                        LocalDateTime.now().getMinute()), "Описание", 0));
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "filter":
                log.info("doPost request.getParameter fromDate: " + request.getParameter("fromDate") + " beforeDate: " +
                        request.getParameter("beforeDate") + " fromDate: " + request.getParameter("fromTime") + " beforeDate: " + request.getParameter("beforeTime"));

                LocalDate ltFromDate = null, ldBeforeDate = null;
                LocalTime ltFromTime, ltBeforeTime;

                if (!request.getParameter("fromDate").isEmpty()) {
                    ltFromDate = LocalDate.parse(request.getParameter("fromDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                }
                if (!request.getParameter("beforeDate").isEmpty()) {
                    ldBeforeDate = LocalDate.parse(request.getParameter("beforeDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                }
                if (!request.getParameter("fromTime").isEmpty()) {
                    ltFromTime = LocalTime.parse(request.getParameter("fromTime"), DateTimeFormatter.ofPattern("HH:mm"));
                } else ltFromTime = LocalTime.MIN;

                if (!request.getParameter("beforeTime").isEmpty()) {
                    ltBeforeTime = LocalTime.parse(request.getParameter("beforeTime"), DateTimeFormatter.ofPattern("HH:mm"));
                } else ltBeforeTime = LocalTime.MAX;

                request.setAttribute("meals", mealRestController.getAllForSelectedDates(ltFromDate, ldBeforeDate, ltFromTime, ltBeforeTime));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals", mealRestController.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
