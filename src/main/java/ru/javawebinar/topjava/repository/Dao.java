package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Dao {

    Meal getById(int id);

    List <Meal> getList();

    void delete(int id);

    void update(Meal meal);

    void add(Meal meal);

    int createNewId();

    int searchIndexInListById(int id);
}
