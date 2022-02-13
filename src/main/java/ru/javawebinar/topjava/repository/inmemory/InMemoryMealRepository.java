package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.SpringMain;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.web.SecurityUtil;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepository implements MealRepository {
    private static final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(10);

//    {
//        MealsUtil.meals.forEach(this::save);
//    }

    static {
        repository.put(1, new Meal(1, 1, LocalDateTime.of(2020, Month.JANUARY, 1, 10, 0), "Завтрак", 500));
        repository.put(2, new Meal(2, 1, LocalDateTime.of(2020, Month.JANUARY, 2, 12, 0), "Обед", 1500));
        repository.put(3, new Meal(3, 2, LocalDateTime.of(2020, Month.JANUARY, 3, 20, 0), "Ужин", 1000));
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            System.out.println("MealRepository add " + meal.getDateTime());
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
//    public Collection<Meal> getAll() {
//        return repository.values();
//    }
    public List<Meal> getAll() {
        List<Meal> meals = new ArrayList<>();
        for (Map.Entry<Integer, Meal> entry : repository.entrySet()) {
            if (entry.getValue().getUserId() == SecurityUtil.authUserId()) {
                meals.add(entry.getValue());
            }
        }
        return meals;
    }
}

