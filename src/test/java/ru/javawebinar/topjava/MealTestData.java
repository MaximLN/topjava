package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int ID = START_SEQ + 1000;

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int GUEST_ID = START_SEQ + 2;
    public static final int NOT_FOUND = 10;

    public static final Meal meal = new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 20, 21, 0), "Админ ужин", 1500);
    public static final Meal mealByDublicate = new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 5, 5, 5), "ByDublicate", 1500);

    public static final List<Meal> checkAdminList = Arrays.asList(
            new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 21, 23, 0), "description7Admin", 1500),
            new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 21, 21, 0), "description6Admin", 200),
            new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 21, 14, 0), "description1Admin", 100),
            new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 21, 12, 0), "description2Admin", 1000),
            new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 20, 11, 0), "descriptio3Admin", 2000),
            new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 18, 9, 0), "description4Admin", 100),
            new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 17, 22, 0), "description5Admin", 1200)
    );
    public static final List<Meal> checkUserFilteredList = Arrays.asList(
            new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 21, 23, 0), "descriptionuser7", 1500),
            new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 21, 21, 0), "descriptionuser6", 200),
            new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 21, 14, 0), "descriptionuser1", 100),
            new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 21, 12, 0), "descriptionuser2", 1000),
            new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 20, 11, 0), "descriptionuser3", 2000)
    );


    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 20, 21, 0), "Админ ужин", 1500);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal);
        updated.setDateTime(LocalDateTime.of(2022, Month.FEBRUARY, 21, 0, 0));
        updated.setDescription("UpdatedDescription");
        updated.setCalories(330);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
        assertThat(actual.getCalories()).isEqualTo(expected.getCalories());
        assertThat(actual.getDateTime()).isEqualTo(expected.getDateTime());
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id").isEqualTo(expected);
    }
}
