package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController extends AbstractMealController {
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping("/meals")
    public String doGet(HttpServletRequest request, Model model) {
        log.info("Get meals");
        model.addAttribute("meals", getAll());
        String result = "meals";
        model.addAttribute("meals", getAll());
        switch (request.getParameter("action") == null ? "all" : request.getParameter("action")) {
            case "delete" -> {
                delete(request.getParameter("id"), model);
                return "redirect:meals";
            }
            case "create", "update" -> result = createOrUpdate(request.getParameter("id"), request.getParameter("action"), model);
            case "filter" -> filter(request.getParameter("startDate"), request.getParameter("endDate"),
                    request.getParameter("startTime"), request.getParameter("endTime"), model);
        }
        return result;
    }

    public void delete(String id, Model model) {
        delete(Integer.parseInt(id));
        model.addAttribute("meals", getAll());
    }

    public String createOrUpdate(String id, String action, Model model) {
        final Meal meal = "create".equals(action) ?
                new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                get(Integer.parseInt(id));
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    public void filter(String startDate, String endDate, String startTime, String endTime, Model model) {
        LocalDate start_Date = parseLocalDate(startDate);
        LocalDate end_Date = parseLocalDate(endDate);
        LocalTime start_Time = parseLocalTime(startTime);
        LocalTime end_Time = parseLocalTime(endTime);
        model.addAttribute("meals", getBetween(start_Date, start_Time, end_Date, end_Time));
    }

    @PostMapping("/meals")
    public String doPost(HttpServletRequest request) {
        log.info("Post meals");
        if (Objects.equals(request.getParameter("id"), "")) {
            create(new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"), Integer.parseInt(request.getParameter("calories"))));
        } else {
            update(new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                            request.getParameter("description"), Integer.parseInt(request.getParameter("calories"))),
                    Integer.parseInt(request.getParameter("id")));
        }
        return "redirect:meals";
    }

}
