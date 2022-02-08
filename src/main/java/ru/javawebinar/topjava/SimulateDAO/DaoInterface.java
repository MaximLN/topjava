package ru.javawebinar.topjava.SimulateDAO;

import ru.javawebinar.topjava.model.Meal;

import java.util.Map;

public interface DaoInterface {

    Map<Long, Meal> getMeals();

    void deleteMeals(Long id);

    void updateMeals(Long id, Meal meal);

    void addMeals(Long key, Meal meal);
}
