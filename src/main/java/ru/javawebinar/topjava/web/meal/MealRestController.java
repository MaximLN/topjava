package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {

    private final MealRepository repository = new InMemoryMealRepository();
    private final MealService mealService = new MealService(repository);
    protected final Logger log = LoggerFactory.getLogger(getClass());

    public List<MealTo> getAll() {
        log.info("getAll");
        List<MealTo> listMealTo = MealsUtil.getTos(mealService.getAll(authUserId()), SecurityUtil.authUserCaloriesPerDay());
        Comparator<MealTo> COMPARE_BY_DATETIME = Comparator.comparing(MealTo::getDateTime).reversed();
        listMealTo.sort(COMPARE_BY_DATETIME);
        return listMealTo;
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return mealService.get(id, authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        meal.setUserId(SecurityUtil.authUserId());
        return mealService.create(meal);
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        meal.setUserId(SecurityUtil.authUserId());
        mealService.update(meal, id);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        mealService.delete(id, authUserId());
    }

    public List<MealTo> getAllForSelectedDates(LocalDate fromDate, LocalDate beforeDate, LocalTime ltFromTime, LocalTime ltBeforeTime) {
        log.info("getFilteredTos: " + fromDate + beforeDate);
        List<MealTo> listMealTo = MealsUtil.getFilteredTos(mealService.getAllForSelectedDates(authUserId(),
                fromDate, beforeDate), SecurityUtil.authUserCaloriesPerDay(), ltFromTime, ltBeforeTime);
        Comparator<MealTo> COMPARE_BY_DATETIME = Comparator.comparing(MealTo::getDateTime).reversed();
        listMealTo.sort(COMPARE_BY_DATETIME);
        return listMealTo;
    }
}