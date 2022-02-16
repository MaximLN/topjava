package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(10);

    static {
        repository.put(1, new Meal(1, 1, LocalDateTime.of(2022, Month.FEBRUARY, 1, 10, 0), "Завтрак", 500));
        repository.put(2, new Meal(2, 1, LocalDateTime.of(2022, Month.FEBRUARY, 2, 12, 57), "Обед", 1500));
        repository.put(3, new Meal(3, 2, LocalDateTime.of(2022, Month.FEBRUARY, 3, 2, 2), "Ужин", 1000));
        repository.put(4, new Meal(4, 1, LocalDateTime.of(2022, Month.FEBRUARY, 4, 21, 3), "Ужин", 1000));
        repository.put(5, new Meal(5, 1, LocalDateTime.of(2022, Month.FEBRUARY, 5, 8, 10), "Ужин", 1000));
        repository.put(6, new Meal(6, 1, LocalDateTime.of(2022, Month.FEBRUARY, 6, 7, 55), "Ужин", 1000));
        repository.put(7, new Meal(7, 2, LocalDateTime.of(2022, Month.FEBRUARY, 7, 10, 1), "Ужин", 1000));
    }

    @Override
    public Meal save(Meal meal, int id) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        } else if (meal.getId() == id & Objects.equals(meal.getUserId(), repository.get(id).getUserId())) {
            return repository.computeIfPresent(meal.getId(), (id1, oldMeal) -> meal);
        }
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = repository.get(id);
        if (meal.getUserId() == userId) {
            return repository.remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        if (meal.getUserId() == userId) {
            return meal;
        } else
            return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        List<Meal> meals = new ArrayList<>();
        for (Map.Entry<Integer, Meal> entry : repository.entrySet()) {
            if (entry.getValue().getUserId() == userId) {
                meals.add(entry.getValue());
            }
        }
        return meals;
    }

    @Override
    public List<Meal> getAllForSelectedDates(int userId, LocalDate fromDate, LocalDate beforeDate) {
        List<Meal> list = new ArrayList<>(getAll(userId));
        List<Meal> listFiltered = new ArrayList<>(getAll(userId));
        for (Meal mealInList : list) {
            if (fromDate != null) {
                if (mealInList.getDate().isBefore(fromDate)) {
                    listFiltered.remove(mealInList);
                }
            }
            if (beforeDate != null) {
                if (mealInList.getDate().isAfter(beforeDate)) {
                    listFiltered.remove(mealInList);
                }
            }
        }
        return listFiltered;
    }
}

