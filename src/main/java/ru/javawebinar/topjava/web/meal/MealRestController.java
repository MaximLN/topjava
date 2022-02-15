package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

@Controller
public class MealRestController {
    private final MealRepository repository = new InMemoryMealRepository();

    protected final Logger log = LoggerFactory.getLogger(getClass());

    public List<MealTo> getAll() {
        log.info("getAll");
        //в параметр + authUserId()
        return MealsUtil.getTos(repository.getAll(), SecurityUtil.authUserCaloriesPerDay());
    }


    public Meal get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }


    public Meal create(Meal meal) {
        log.info("create {}", meal);
        return repository.save(meal);
    }


    public void delete(int id) {
        log.info("delete {}", id);
        repository.delete(id);
    }


//    public void update(Meal meal, int id) {
//        log.info("update {} with id={}", meal, id);
//        repository.save(meal, id);
//    }

}