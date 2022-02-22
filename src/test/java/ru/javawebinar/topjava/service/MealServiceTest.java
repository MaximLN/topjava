package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() {
        Meal created = service.create(getNew(), ADMIN_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, ADMIN_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        service.create(mealByDublicate, ADMIN_ID);
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 5, 5, 5), "After duplicate", 350), ADMIN_ID));
    }

    @Test
    public void delete() {
        Meal created = service.create(getNew(), ADMIN_ID);
        Integer newId = created.getId();
        service.delete(newId, ADMIN_ID);
        assertThrows(NotFoundException.class, () -> service.get(newId, ADMIN_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, NOT_FOUND));
    }

    @Test
    public void get() {
//        Create new meal of static meal
        Meal created = service.create(getNew(), ADMIN_ID);
        Integer newId = created.getId();
        Meal meal = service.get(newId, ADMIN_ID);
        assertMatch(meal, MealTestData.meal);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, NOT_FOUND));
    }

    @Test
    public void update() {
//        Create new meal of static meal
        Meal created = service.create(getNew(), ADMIN_ID);
        Integer newId = created.getId();
//        Custom meal
        Meal updated = getUpdated();
        updated.setId(newId);
        service.update(updated, ADMIN_ID);
        assertMatch(service.get(newId, ADMIN_ID), getUpdated());
    }

    @Test
    public void getAll() {
        service.create(meal, ADMIN_ID);
        List<Meal> all = service.getAll(ADMIN_ID);
        assertMatch(all, meal);
    }

    @Test
    public void getBetweenHalfOpen() {
        List<Meal> all = service.getBetweenInclusive(LocalDate.of(2022, Month.FEBRUARY, 20), LocalDate.of(2022, Month.FEBRUARY, 25), ADMIN_ID);
        assertMatch(all, meal);
    }

}