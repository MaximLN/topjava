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
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
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
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, ADMIN_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин duplicate", 350), ADMIN_ID));
    }

    @Test
    public void delete() {
        Meal created = service.create(getNew(), ADMIN_ID);
        Integer newId = created.getId();
        service.delete(newId, ADMIN_ID);
        assertThrows(NotFoundException.class, () -> service.get(newId, ADMIN_ID));
    }
//
//    @Test
//    public void deletedNotFound() {
//        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
//    }
//
//    @Test
//    public void get() {
//        User user = service.get(USER_ID);
//        assertMatch(user, UserTestData.user);
//    }
//
//    @Test
//    public void getNotFound() {
//        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
//    }
//
//    @Test
//    public void getByEmail() {
//        User user = service.getByEmail("admin@gmail.com");
//        assertMatch(user, admin);
//    }
//
//    @Test
//    public void update() {
//        User updated = getUpdated();
//        service.update(updated);
//        assertMatch(service.get(USER_ID), getUpdated());
//    }
//
//    @Test
//    public void getAll() {
//        List<User> all = service.getAll();
//        assertMatch(all, admin, guest, user);
//    }
}