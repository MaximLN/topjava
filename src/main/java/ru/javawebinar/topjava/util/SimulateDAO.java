package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimulateDAO {
    static int caloriesPerDay = 2000;
    static List<Meal> meals = new ArrayList(Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    ));

    public static List<Meal> getMeals() {
        return meals;
    }

    public static int getCaloriesPerDay() {
        return caloriesPerDay;
    }

    public static void deleteMeals(int id) {
        meals.remove(id);
    }

    public static void updateMeals(int id, Meal meal) {
        meals.set(id, meal);
    }

    public static void addMeals(Meal meal) {
        meals.add(meal);
    }
}
