package ru.javawebinar.topjava.SimulateDAO;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DaoImpl implements DaoInterface {

    static ConcurrentHashMap<Long, Meal> mealMap = new ConcurrentHashMap<>();

    static {
        mealMap.put(1L, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        mealMap.put(3L, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        mealMap.put(4L, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        mealMap.put(5L, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        mealMap.put(6L, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        mealMap.put(7L, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        mealMap.put(8L, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public Map<Long, Meal> getMeals() {
        return mealMap;
    }

    @Override
    public void deleteMeals(Long id) {
        mealMap.remove(id);
    }

    @Override
    public void updateMeals(Long id, Meal meal) {
        mealMap.put(id, meal);
    }

    @Override
    public void addMeals(Long id, Meal meal) {
        mealMap.put(id, meal);
    }

}
