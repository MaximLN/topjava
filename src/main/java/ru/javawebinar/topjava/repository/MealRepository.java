package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface MealRepository {
    // null if updated meal does not belong to userId
    Meal create(Meal meal);
//    Meal save(Meal meal, int id, int userId);

    Meal update(Meal meal, int id, int userId);

    // false if meal does not belong to userId
    boolean delete(int id, int userId);

    // null if meal does not belong to userId
    Meal get(int id, int userId);

    // ORDERED dateTime desc
    Collection<Meal> getAll(int userId);

    List<Meal> getAllForSelectedDates (int userId, LocalDate fromDate, LocalDate beforeDate);
}
