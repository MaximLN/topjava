package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController {
    private static final Logger log = LoggerFactory.getLogger(RootController.class);

    @Autowired
    private MealRestController mealController;

    @GetMapping("/meals")
    public String doGet(
            @RequestParam(required = false) String action,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            Model model) {
        String result = "meals";
        model.addAttribute("meals", mealController.getAll());
        switch (action == null ? "all" : action) {
            case "delete" -> {
                mealController.delete(Integer.parseInt(id));
                model.addAttribute("meals", mealController.getAll());
            }
            case "create", "update" -> {
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealController.get(Integer.parseInt(id));
                model.addAttribute("meal", meal);
                result = "mealForm";
            }
            case "filter" -> {
                LocalDate start_Date = parseLocalDate(startDate);
                LocalDate end_Date = parseLocalDate(endDate);
                LocalTime start_Time = parseLocalTime(startTime);
                LocalTime end_Time = parseLocalTime(endTime);
                model.addAttribute("meals", mealController.getBetween(start_Date, start_Time, end_Date, end_Time));
            }
        }
        return result;
    }

}
