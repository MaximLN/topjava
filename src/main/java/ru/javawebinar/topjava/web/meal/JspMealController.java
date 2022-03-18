package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

@RequestMapping("/meals")
@Controller
public class JspMealController extends AbstractMealController {
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping("")
    public String doGet(Model model) {
        model.addAttribute("meals", getAll());
        return "meals";
    }

    @GetMapping("/update")
    String update(HttpServletRequest request, Model model) {
        final Meal meal = request.getParameter("id") == null ?
                new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                get(Integer.parseInt(request.getParameter("id")));
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/delete")
    String delete(HttpServletRequest request, Model model) {
        delete(Integer.parseInt(request.getParameter("id")));
        model.addAttribute("meals", getAll());
        return "redirect:/meals";
    }

    @GetMapping("/filter")
    String filter(HttpServletRequest request, Model model) {
        LocalDate start_Date = parseLocalDate(request.getParameter("startDate"));
        LocalDate end_Date = parseLocalDate(request.getParameter("endDate"));
        LocalTime start_Time = parseLocalTime(request.getParameter("startTime"));
        LocalTime end_Time = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals", getBetween(start_Date, start_Time, end_Date, end_Time));
        return "meals";
    }

    @PostMapping("")
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
        return "redirect:/meals";
    }

}
