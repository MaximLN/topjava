package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.RootController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController {
    private static final Logger log = LoggerFactory.getLogger(RootController.class);

    @Autowired
    private AbstractMealController abstractMealController;

    @GetMapping("/meals")
    public String doGet(
            @RequestParam(required = false) String action,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            Model model) {
        log.info("doGet meals");
        String result = "meals";
        model.addAttribute("meals", abstractMealController.getAll());
        switch (action == null ? "all" : action) {
            case "delete" -> delete(id, model);
            case "create", "update" -> result = createOrUpdate(id, action, model);
            case "filter" -> filter(startDate, endDate, startTime, endTime, model);
        }
        return result;
    }

    public void delete(String id, Model model) {
        abstractMealController.delete(Integer.parseInt(id));
        model.addAttribute("meals", abstractMealController.getAll());
    }

    public String createOrUpdate(String id, String action, Model model) {
        final Meal meal = "create".equals(action) ?
                new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                abstractMealController.get(Integer.parseInt(id));
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    public void filter(String startDate, String endDate, String startTime, String endTime, Model model) {
        LocalDate start_Date = parseLocalDate(startDate);
        LocalDate end_Date = parseLocalDate(endDate);
        LocalTime start_Time = parseLocalTime(startTime);
        LocalTime end_Time = parseLocalTime(endTime);
        model.addAttribute("meals", abstractMealController.getBetween(start_Date, start_Time, end_Date, end_Time));
    }

    @PostMapping("/meals")
    public String doGet(HttpServletRequest request) {
        log.info("Post meals");
        if (Objects.equals(request.getParameter("id"), "")) {
            abstractMealController.create(new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"), Integer.parseInt(request.getParameter("calories"))));
        } else {
            abstractMealController.update(new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"), Integer.parseInt(request.getParameter("calories"))), Integer.parseInt(request.getParameter("id")));
        }
        return "redirect:meals";
    }

}
