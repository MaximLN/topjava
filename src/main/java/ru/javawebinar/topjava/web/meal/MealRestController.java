package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.SpringMain;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {

    private final MealRepository repository = new InMemoryMealRepository();
    MealService mealService = new MealService(repository);

    protected final Logger log = LoggerFactory.getLogger(getClass());

    public List<MealTo> getAll() {
        log.info("getAll");
        List<MealTo> listMealTo = MealsUtil.getTos(mealService.getAll(authUserId()), SecurityUtil.authUserCaloriesPerDay());
        listMealTo.sort(MealTo.COMPARE_BY_DATETIME);
        return listMealTo;
    }


    public Meal get(int id) {
        log.info("get {}", id);
        return mealService.get(id, authUserId());
    }


    public Meal save(Meal meal) {
        log.info("create {}", meal);
        meal.setUserId(SecurityUtil.authUserId());
        if (meal.getId() == null) {
            return mealService.create(meal);
        } else mealService.update(meal, authUserId());
        ////void???
        return meal;
    }


    public void delete(int id) {
        log.info("delete {}", id);
        mealService.delete(id, authUserId());
    }

    public List<MealTo> getAllForSelectedDates(LocalDate fromDate, LocalDate beforeDate, LocalTime ltFromTime, LocalTime ltBeforeTime) {
        log.info("getFilteredTos: " + fromDate + beforeDate);
        List<MealTo> listMealTo = MealsUtil.getFilteredTos(mealService.getAllForSelectedDates(authUserId(),
                fromDate, beforeDate), SecurityUtil.authUserCaloriesPerDay(), ltFromTime, ltBeforeTime);
        listMealTo.sort(MealTo.COMPARE_BY_DATETIME);
        return listMealTo;
    }
}