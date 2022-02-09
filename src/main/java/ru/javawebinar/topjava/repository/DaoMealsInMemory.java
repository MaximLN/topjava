package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class DaoMealsInMemory implements Dao {

    private static final CopyOnWriteArrayList<Meal> list = new CopyOnWriteArrayList<>();

    static {
        list.add(new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        list.add(new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        list.add(new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        list.add(new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        list.add(new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        list.add(new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        list.add(new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
//    The method is not used, but it had to be added. Probably for the future.
    public Meal getById(int id) {
        for (Meal meal : list) {
            if (meal.getId() == id) {
                return list.get(id);
            }
        }
        return null;
    }

    @Override
    public List<Meal> getList() {
        return new ArrayList<>(list);
    }

    @Override
    public void delete(int id) {
        int indexDeleteElement = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                indexDeleteElement = i;
            }
        }
        if (indexDeleteElement != -1) {
            list.remove(indexDeleteElement);
        }
    }

    @Override
    public void update(Meal meal) {
        int indexUpdateElement = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == meal.getId()) {
                indexUpdateElement = i;
            }
        }
        if (indexUpdateElement != -1) {
            list.set(indexUpdateElement, meal);
        }
    }

    @Override
    public void add(Meal meal) {
        list.add(meal);
    }

    @Override
    public int createNewId() {
        return (list.get(list.size() - 1).getId()) + 1;
    }

    @Override
    public int searchIndexInListById(int id) {
        int indexInList = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                indexInList = i;
            }
            if (indexInList != -1) {
                return indexInList;
            }
        }
        return -1;
    }
}
