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
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)

@Sql(scripts = {"classpath:db/initDB.sql","classpath:db/populateDB.sql"}, config = @SqlConfig(encoding = "UTF-8"))
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
        Meal created = service.create(getNew(), UserTestData.ADMIN_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(100017);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, UserTestData.ADMIN_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        service.create(mealByDublicate, UserTestData.ADMIN_ID);
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(adminMeal1.getDateTime(), "After duplicate", 350), UserTestData.ADMIN_ID));
    }

    @Test
    public void delete() {
        service.delete(ID10, UserTestData.ADMIN_ID);
        assertThrows(NotFoundException.class, () -> service.get(ID10, UserTestData.ADMIN_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(UserTestData.NOT_FOUND, UserTestData.NOT_FOUND));
    }


    @Test
    public void get() {
        Meal meal = service.get(ID15, UserTestData.ADMIN_ID);
        assertMatch(meal, adminMeal2);
    }

    @Test
    public void getAlien() {
        assertThrows(NotFoundException.class, () -> service.get(ID15, UserTestData.USER_ID));
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(UserTestData.NOT_FOUND, UserTestData.NOT_FOUND));
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        updated.setId(ID15);
        service.update(updated, UserTestData.ADMIN_ID);
        assertMatch(service.get(ID15, UserTestData.ADMIN_ID), updated);
    }

//    @Test
//    public void updateAlien() {
//        Meal updated = getUpdated();
//        updated.setId(ID15);
//        System.out.println("______________________________________________________"+updated);
//        System.out.println(service.get(ID15,UserTestData.ADMIN_ID));
//        assertThrows(NotFoundException.class, () -> service.update(updated, UserTestData.USER_ID));
//    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(UserTestData.ADMIN_ID);
        assertMatch(all, adminList);
    }

    @Test
    public void getBetweenHalfOpen() {
        List<Meal> all = service.getBetweenInclusive(LocalDate.of(2022, Month.FEBRUARY, 20), LocalDate.of(2022, Month.FEBRUARY, 21), UserTestData.USER_ID);
        assertMatch(all, userFilteredList);
    }

}